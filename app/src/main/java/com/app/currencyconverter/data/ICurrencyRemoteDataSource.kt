package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse
import com.app.currencyconverter.data.response.LatestRatesResponse

interface ICurrencyRemoteDataSource {
    suspend fun getAllCurrencies() : CurrenciesResponse

    suspend fun getLatestRate(): LatestRatesResponse
}