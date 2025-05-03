package com.app.currencyconverter.application.extensions

import com.app.currencyconverter.domain.state.DataState
import com.app.currencyconverter.domain.state.StateError

inline fun <T> DataState<T>.onLoading(loadingHandler: (Boolean) -> Unit): DataState<T> {
    loadingHandler(loading)
    return this
}

inline fun <T> DataState<T>.onError(errorHandler: (StateError) -> Unit): DataState<T> {
    error?.let { errorHandler(it) }
    return this
}

inline fun <T> DataState<T>.onSuccess(successHandler: (T) -> Unit): DataState<T> {
    data?.let { successHandler(it) }
    return this
}