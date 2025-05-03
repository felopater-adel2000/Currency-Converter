package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse

class CurrencyRemoteDataSource(
    private val apiInterface: ApiInterface
) : ICurrencyRemoteDataSource {
    override suspend fun getAllCurrencies(): CurrenciesResponse {
       return apiInterface.getAllCurrencies()
    }
}