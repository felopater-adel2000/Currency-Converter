package com.app.currencyconverter.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class LatestRatesResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("timestamp")
    @Expose
    val timestamp: Long? = null,

    @SerializedName("base")
    @Expose
    val base: String? = null,

    @SerializedName("date")
    @Expose
    val date: String? = null,

    @SerializedName("rates")
    @Expose
    val rates: Map<String, Double>? = null,

    @SerializedName("error")
    @Expose
    val error: ErrorResponse? = null
)