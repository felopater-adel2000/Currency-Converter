package com.app.currencyconverter.application.extensions

fun Int?.orDefault(default: Int = 0) = this ?: default