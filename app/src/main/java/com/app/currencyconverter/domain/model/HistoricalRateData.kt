package com.app.currencyconverter.domain.model

data class HistoricalRateData(
    val date: String,
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double
)