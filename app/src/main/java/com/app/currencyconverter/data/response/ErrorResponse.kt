package com.app.currencyconverter.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ErrorResponse(
    @SerializedName("code")
    @Expose
    val code: Int? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null
)