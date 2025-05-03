package com.app.currencyconverter.application.extensions

fun String?.orDefault(default: String = ""): String {
    return this ?: default
}