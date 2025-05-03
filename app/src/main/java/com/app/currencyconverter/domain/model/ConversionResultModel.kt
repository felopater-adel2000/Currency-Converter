package com.app.currencyconverter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConversionResultModel(
    val fromCurrency: String,
    val toCurrency: String,
    val amount: Double,
    val convertedAmount: Double,
    val rate: Double,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable