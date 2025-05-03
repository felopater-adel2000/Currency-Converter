package com.app.currencyconverter.application.utils

import com.app.currencyconverter.domain.state.DataState
import com.app.currencyconverter.domain.state.StateError

interface DataStateChangeListener {
    fun onErrorStateChange(stateError: StateError)
}