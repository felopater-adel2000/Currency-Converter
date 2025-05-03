package com.app.currencyconverter.application.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.currencyconverter.application.utils.SingleLiveEvent
import com.app.currencyconverter.domain.state.StateError

open class BaseViewModel : ViewModel() {
    protected val error = SingleLiveEvent<StateError?>()
    protected val loading = SingleLiveEvent<Boolean>()

    fun getErrorLiveData(): LiveData<StateError?> = error

    fun getLoading(): LiveData<Boolean> = loading
}