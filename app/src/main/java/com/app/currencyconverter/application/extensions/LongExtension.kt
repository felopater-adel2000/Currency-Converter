package com.app.currencyconverter.application.extensions


fun Long?.orDefault(default: Long = 0L): Long {
    return this ?: default
}