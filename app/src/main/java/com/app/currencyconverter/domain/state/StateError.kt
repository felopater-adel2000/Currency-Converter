package com.app.currencyconverter.domain.state

data class StateError(
    val response: Response?,
    val statusCode: Int
)