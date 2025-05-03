package com.app.currencyconverter.domain.repository

import com.app.currencyconverter.domain.model.CurrencyModel
import com.app.currencyconverter.domain.model.ExchangeRateModel
import com.app.currencyconverter.domain.state.DataState

interface ICurrencyRepository {
    suspend fun getAllCurrencies(): DataState<List<CurrencyModel>>

    suspend fun getLatestRates(): DataState<ExchangeRateModel>
}