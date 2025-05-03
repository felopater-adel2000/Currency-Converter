package com.app.currencyconverter.presentation.currencyconverter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.currencyconverter.R
import com.app.currencyconverter.application.base.BaseFragment
import com.app.currencyconverter.application.extensions.displayToast
import com.app.currencyconverter.application.extensions.hide
import com.app.currencyconverter.application.extensions.orDefault
import com.app.currencyconverter.application.extensions.show
import com.app.currencyconverter.application.extensions.singleChoiceStringDialog
import com.app.currencyconverter.databinding.FragmentCurrencyConverterBinding
import com.app.currencyconverter.domain.state.StateError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : BaseFragment<FragmentCurrencyConverterBinding, CurrencyConverterViewModel>(), View.OnClickListener {
    override val viewModel: CurrencyConverterViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.getCurrenciesAndRates()
    }

    private fun initViews() {
        binding.apply {

            etSourceCurrencyAmount.doAfterTextChanged {
                val enteredAmount = it.toString()
                if (enteredAmount.isNotEmpty()) {
                    val amount = enteredAmount.toDouble()
                    viewModel.convertCurrency(amount)
                }
            }

            btnShowHistory.setOnClickListener(this@CurrencyConverterFragment)
            layoutSourceCurrency.setOnClickListener(this@CurrencyConverterFragment)
            etSourceCurrency.setOnClickListener(this@CurrencyConverterFragment)
            layoutDestCurrency.setOnClickListener(this@CurrencyConverterFragment)
            etDestCurrency.setOnClickListener(this@CurrencyConverterFragment)
            ivSwap.setOnClickListener(this@CurrencyConverterFragment)
        }
    }

    private fun initObservers() {
        viewModel.getLoading().observe(viewLifecycleOwner) { showProgressbar(it) }

        viewModel.getErrorLiveData().observe(viewLifecycleOwner) { stateError -> showErrorUI(stateError) }

        viewModel.sourceCurrency.observe(viewLifecycleOwner) { binding.etSourceCurrency.setText(it.code)}
        viewModel.destinationCurrency.observe(viewLifecycleOwner) { binding.etDestCurrency.setText(it.code)}

        viewModel.destinationAmount.observe(viewLifecycleOwner) { binding.etDestCurrencyAmount.setText((it ?: 0.0).toString()) }
    }

    private fun showProgressbar(show: Boolean) {
        binding.layoutLoading.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showErrorUI(
        stateError: StateError?
    ) {
        binding.apply {
            if (stateError != null) {
                clContentContainer.hide()
                layoutError.root.show()
                layoutError.ivError.setImageResource(R.drawable.ic_error)
                binding.layoutError.tvTitle.text = getString(R.string.something_went_wrong)
                binding.layoutError.tvMsg.text = stateError.response?.message.orDefault()
                binding.layoutError.btnReload.setOnClickListener {
                    viewModel.getCurrenciesAndRates()
                }
            } else {
                clContentContainer.show()
                layoutError.root.hide()
            }
        }
    }

    private fun showCurrencyListDialog(type: CurrencyType) {
        requireContext().singleChoiceStringDialog(
            title = getString(R.string.select_currency),
            list = viewModel.currenciesList.map { "${it.code} - ${it.name}" },
            pos = 0
        ) {selectedIndex ->
            when (type) {
                CurrencyType.SOURCE -> viewModel.setSourceCurrency(viewModel.currenciesList[selectedIndex])
                CurrencyType.DESTINATION -> viewModel.setDestinationCurrency(viewModel.currenciesList[selectedIndex])
            }
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCurrencyConverterBinding? {
        return FragmentCurrencyConverterBinding.inflate(inflater, container, attachToParent)
    }

    override fun onClick(v: View?) {
        binding.apply {
            when(v) {
                btnShowHistory -> {
                    if(viewModel.sourceCurrency.value == null || viewModel.destinationCurrency.value == null) {
                        requireContext().displayToast("Please select both currencies first.")
                        return
                    }
                    findNavController().navigate(
                        CurrencyConverterFragmentDirections.actionCurrencyConverterFragmentToRateHistoryFragment(
                            viewModel.sourceCurrency.value?.code.orEmpty(),
                            viewModel.destinationCurrency.value?.code.orEmpty()
                        )
                    )
                }

                layoutSourceCurrency, etSourceCurrency -> showCurrencyListDialog(CurrencyType.SOURCE)

                layoutDestCurrency, etDestCurrency -> showCurrencyListDialog(CurrencyType.DESTINATION)

                ivSwap -> viewModel.swapCurrencies()
            }
        }
    }

    private enum class CurrencyType {
        SOURCE, DESTINATION
    }

}