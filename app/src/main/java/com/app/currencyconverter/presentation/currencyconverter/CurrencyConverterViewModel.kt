package com.app.currencyconverter.presentation.currencyconverter

import com.app.currencyconverter.application.base.BaseViewModel
import com.app.currencyconverter.domain.repository.ICurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repo: ICurrencyRepository
) : BaseViewModel() {
}