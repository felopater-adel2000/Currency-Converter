package com.app.currencyconverter.domain.state

data class Response(
    val message: String? = null,
    val responseType: ResponseType = ResponseType.None()
)

sealed class ResponseType {
    class Toast : ResponseType()
    class Dialog : ResponseType()
    class SnakeBar : ResponseType()
    class None : ResponseType()
}