package com.app.currencyconverter.presentation.currencyconverter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.currencyconverter.R
import com.app.currencyconverter.application.base.BaseFragment
import com.app.currencyconverter.databinding.FragmentCurrencyConverterBinding


class CurrencyConverterFragment : BaseFragment<FragmentCurrencyConverterBinding, CurrencyConverterViewModel>() {
    override val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCurrencyConverterBinding? {
        return FragmentCurrencyConverterBinding.inflate(inflater, container, attachToParent)
    }

}