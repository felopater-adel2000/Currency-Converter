package com.app.currencyconverter.presentation.ratehistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.application.base.BaseViewModel
import com.app.currencyconverter.domain.model.HistoricalRateData
import com.app.currencyconverter.domain.repository.IRateHistoryRepository
import com.app.currencyconverter.domain.state.DataState
import com.app.currencyconverter.domain.state.StateError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RateHistoryViewModel @Inject constructor(
    private val repo: IRateHistoryRepository
) : BaseViewModel() {

    private val _historicalRates = MutableLiveData<List<HistoricalRateData>>()
    val historicalRates: LiveData<List<HistoricalRateData>> = _historicalRates

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getHistoricalRates(fromCurrency: String, toCurrency: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val calendar = Calendar.getInstance()
                val dates = mutableListOf<String>()
                
                // Get the last 4 days
                for (i in 0 until 4) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    dates.add(dateFormat.format(calendar.time))
                }
                
                val historicalRatesList = mutableListOf<HistoricalRateData>()
                
                // Fetch data for each day
                dates.forEach { date ->
                    when (val result = repo.getHistoricalRates(date, fromCurrency, toCurrency)) {
                        is DataState.Success -> {
                            result.data?.let { historicalRateData ->
                                historicalRatesList.add(historicalRateData)
                            }
                        }
                        is DataState.Error -> {
                            val error = result.error
                            _errorMessage.postValue(error?.response?.message ?: "Unknown error occurred")
                        }
                        else -> {}
                    }
                }
                
                _historicalRates.postValue(historicalRatesList)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Unknown error occurred")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}