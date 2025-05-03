package com.app.currencyconverter.presentation.currencyconverter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.application.base.BaseViewModel
import com.app.currencyconverter.application.extensions.onError
import com.app.currencyconverter.application.extensions.onSuccess
import com.app.currencyconverter.application.extensions.orDefault
import com.app.currencyconverter.application.utils.SingleLiveEvent
import com.app.currencyconverter.domain.model.CurrencyModel
import com.app.currencyconverter.domain.model.ExchangeRateModel
import com.app.currencyconverter.domain.repository.ICurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repo: ICurrencyRepository
) : BaseViewModel() {

    private val _sourceCurrency = SingleLiveEvent<CurrencyModel>()
    val sourceCurrency: LiveData<CurrencyModel> = _sourceCurrency

    private val _destinationCurrency = SingleLiveEvent<CurrencyModel>()
    val destinationCurrency: LiveData<CurrencyModel> = _destinationCurrency

    private val _currenciesList = ArrayList<CurrencyModel>()
    val currenciesList: List<CurrencyModel> = _currenciesList

    private val _destinationAmount = SingleLiveEvent<Double>()
    val destinationAmount: LiveData<Double> = _destinationAmount

    private var exchangeRate: ExchangeRateModel? = null
    private var amount: Double = 0.0

    fun getCurrenciesAndRates() {
        viewModelScope.launch {
            if(_currenciesList.isEmpty() || exchangeRate == null) {
                loading.postValue(true)

                val currencies = async { repo.getAllCurrencies() }
                val exchangeRates = async { repo.getLatestRates() }

                currencies.await().let { state ->
                    Log.d("Felo", "getCurrenciesAndRates: ${state.data}")
                    state.onSuccess { list ->
                        error.postValue(null)
                        _currenciesList.clear()
                        _currenciesList.addAll(list)
                    }.onError { stateError -> error.postValue(stateError) }
                }

                exchangeRates.await().let { state ->
                    state.onSuccess { rate ->
                        error.postValue(null)
                        exchangeRate = rate
                    }.onError { stateError -> error.postValue(stateError) }
                }

                loading.postValue(false)
            }
        }
    }


    private fun convertAndPost(
        amount: Double,
        fromCurrency: String,
        toCurrency: String,
        rates: Map<String, Double>,
    ) {
        val convertedAmount = when {
            // Case 1: Converting from EUR to another currency
            fromCurrency == "EUR" -> {
                val toRate = rates[toCurrency] ?: return
                amount * toRate
            }

            // Case 2: Converting to EUR from another currency
            toCurrency == "EUR" -> {
                val fromRate = rates[fromCurrency] ?: return
                amount / fromRate
            }

            // Case 3: Converting between two non-EUR currencies
            else -> {
                val fromRate = rates[fromCurrency] ?: return
                val toRate = rates[toCurrency] ?: return
                // Convert to EUR first, then to target currency
                (amount / fromRate) * toRate
            }
        }

        _destinationAmount.postValue(convertedAmount)
    }

    fun setSourceCurrency(model: CurrencyModel) {
        _sourceCurrency.postValue(model)
        convertCurrency(amount)
    }

    fun setDestinationCurrency(model: CurrencyModel) {
        _destinationCurrency.postValue(model)
        convertCurrency(amount)
    }

    fun convertCurrency(amount: Double) {
        this.amount = amount
        convertAndPost(
            amount = amount,
            fromCurrency = _sourceCurrency.value?.code.orDefault(),
            toCurrency = _destinationCurrency.value?.code.orDefault(),
            rates = exchangeRate?.rates ?: return
        )
    }


    fun swapCurrencies() {
        val temp = _sourceCurrency.value
        _sourceCurrency.postValue(_destinationCurrency.value)
        temp?.let { _destinationCurrency.postValue(temp) }
        convertCurrency(_destinationAmount.value ?: 0.0)
    }
}