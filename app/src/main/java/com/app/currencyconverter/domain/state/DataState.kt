package com.app.currencyconverter.domain.state

sealed class DataState<T>(
    var error: StateError? = null,
    var loading: Boolean = false,
    var data: T? = null,
    var statusCode: Int? = null
) {

    class Success<T>(
        data: T? = null,
        response: Response? = null,
    ) : DataState<T>(
        data = data,
        loading = false
    )

    class Error<T>(
        response: Response?,
        statusCode: Int
    ) : DataState<T>(
        error = StateError(response, statusCode),
        loading = false,
        statusCode = statusCode
    )

    class Loading<T>(
        isLoading: Boolean,
        cashedData: T? = null
    ) : DataState<T>(
        loading = isLoading,
        data = cashedData
    )

}