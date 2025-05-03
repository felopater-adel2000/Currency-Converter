package com.app.currencyconverter.data

import com.app.currencyconverter.data.response.CurrenciesResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("symbols")
    suspend fun getAllCurrencies(): CurrenciesResponse

}