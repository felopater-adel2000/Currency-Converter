package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse

interface ICurrencyRemoteDataSource {
    suspend fun getAllCurrencies() : CurrenciesResponse
}