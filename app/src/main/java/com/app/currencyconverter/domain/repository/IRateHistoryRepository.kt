package com.app.currencyconverter.domain.repository

import com.app.currencyconverter.domain.model.HistoricalRateData
import com.app.currencyconverter.domain.state.DataState

interface IRateHistoryRepository {
    suspend fun getHistoricalRates(
        date: String,
        fromCurrency: String,
        toCurrency: String
    ): DataState<HistoricalRateData>
}