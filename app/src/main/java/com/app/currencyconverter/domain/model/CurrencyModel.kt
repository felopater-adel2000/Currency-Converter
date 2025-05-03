package com.app.currencyconverter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyModel(
    val code: String,
    val name: String
) : Parcelable