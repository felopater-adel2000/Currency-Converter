package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse
import com.app.currencyconverter.data.response.LatestRatesResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("symbols")
    suspend fun getAllCurrencies(): CurrenciesResponse

    @GET("latest")
    suspend fun getLatestRates(): LatestRatesResponse

}