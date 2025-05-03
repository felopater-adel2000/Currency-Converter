package com.app.currencyconverter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExchangeRateModel(
    val timestamp: Long,
    val baseCurrency: String,
    val date: String,
    val rates: Map<String, Double>
) : Parcelable