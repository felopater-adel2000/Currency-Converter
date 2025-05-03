package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse
import com.app.currencyconverter.data.response.HistoricalRatesResponse
import com.app.currencyconverter.data.response.LatestRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("symbols")
    suspend fun getAllCurrencies(): CurrenciesResponse

    @GET("latest")
    suspend fun getLatestRates(): LatestRatesResponse

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("symbols") symbols: String
    ): HistoricalRatesResponse
}