package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse
import com.app.currencyconverter.data.response.LatestRatesResponse

class CurrencyRemoteDataSource(
    private val apiInterface: ApiInterface
) : ICurrencyRemoteDataSource {
    override suspend fun getAllCurrencies(): CurrenciesResponse {
       return apiInterface.getAllCurrencies()
    }

    override suspend fun getLatestRate(): LatestRatesResponse {
        return apiInterface.getLatestRates()
    }
}