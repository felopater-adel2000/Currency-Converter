package com.app.currencyconverter.data

import com.app.currencyconverter.application.extensions.orDefault
import com.app.currencyconverter.domain.model.HistoricalRateData
import com.app.currencyconverter.domain.repository.IRateHistoryRepository
import com.app.currencyconverter.domain.state.DataState
import com.app.currencyconverter.domain.state.Response
import com.app.currencyconverter.domain.state.ResponseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RateHistoryRepository @Inject constructor(
    private val remoteDataSource: ICurrencyRemoteDataSource
) : IRateHistoryRepository {
    
    override suspend fun getHistoricalRates(
        date: String,
        fromCurrency: String,
        toCurrency: String
    ): DataState<HistoricalRateData> {
        return try {
            val symbols = "$fromCurrency,$toCurrency"
            val response = remoteDataSource.getHistoricalRates(date, symbols)
            
            if (response.success == true) {
                val rates = response.rates
                if (rates != null) {
                    val toRate = rates[toCurrency] ?: 0.0
                    val fromRate = rates[fromCurrency] ?: 1.0
                    val rate = toRate / fromRate
                    
                    DataState.Success(
                        data = HistoricalRateData(
                            date = response.date.orDefault(date),
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency,
                            rate = rate
                        )
                    )
                } else {
                    DataState.Error(
                        response = Response(
                            message = "No rate data available",
                            responseType = ResponseType.Dialog()
                        ),
                        statusCode = 0
                    )
                }
            } else {
                DataState.Error(
                    response = Response(
                        message = response.error?.type.orDefault("An unexpected error occurred"),
                        responseType = ResponseType.Dialog()
                    ),
                    statusCode = response.error?.code.orDefault(0)
                )
            }
        } catch (e: Throwable) {
            DataState.Error(
                response = Response(
                    message = e.message.orDefault("An unexpected error occurred"),
                    responseType = ResponseType.Dialog()
                ),
                statusCode = 0
            )
        }
    }
}