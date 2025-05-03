package com.app.currencyconverter.data

import android.provider.ContactsContract
import com.app.currencyconverter.application.extensions.orDefault
import com.app.currencyconverter.domain.model.CurrencyModel
import com.app.currencyconverter.domain.model.ExchangeRateModel
import com.app.currencyconverter.domain.repository.ICurrencyRepository
import com.app.currencyconverter.domain.state.DataState
import com.app.currencyconverter.domain.state.Response
import com.app.currencyconverter.domain.state.ResponseType

class CurrencyRepository (
    private val remoteDataSource: ICurrencyRemoteDataSource
) : ICurrencyRepository {
    override suspend fun getAllCurrencies(): DataState<List<CurrencyModel>> {
        return try {

            val response = remoteDataSource.getAllCurrencies()
            if(response.success == true) {
                DataState.Success(
                    data = response.symbols?.map {
                        CurrencyModel(
                            code = it.key,
                            name = it.value
                        )
                    }
                )
            }
            else {
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

    override suspend fun getLatestRates(): DataState<ExchangeRateModel> {
        TODO("Not yet implemented")
    }
}