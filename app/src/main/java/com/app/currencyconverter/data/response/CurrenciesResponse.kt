package com.app.currencyconverter.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrenciesResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("symbols")
    @Expose
    val symbols: Map<String, String>? = null,

    @SerializedName("error")
    @Expose
    val error: ErrorResponse? = null
)