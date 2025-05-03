package com.app.currencyconverter.presentation.ratehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.currencyconverter.application.base.BaseFragment
import com.app.currencyconverter.databinding.FragmentRateHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateHistoryFragment : BaseFragment<FragmentRateHistoryBinding, RateHistoryViewModel>() {
    override val viewModel: RateHistoryViewModel by viewModels()
    private val args: RateHistoryFragmentArgs by navArgs()
    private lateinit var adapter: HistoricalRatesAdapter
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
        loadHistoricalRates()
    }
    
    private fun setupViews() {
        adapter = HistoricalRatesAdapter()
        binding.rvHistoricalRates.adapter = adapter
        
        binding.tvCurrencyPair.text = "${args.sourceCurrency} / ${args.destCurrency}"
    }
    
    private fun observeViewModel() {
        viewModel.historicalRates.observe(viewLifecycleOwner) { rates ->
            adapter.submitList(rates)
            binding.rvHistoricalRates.isVisible = rates.isNotEmpty()
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
        
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            binding.tvError.isVisible = errorMessage.isNotEmpty()
            binding.tvError.text = errorMessage
        }
    }
    
    private fun loadHistoricalRates() {
        viewModel.getHistoricalRates(args.sourceCurrency, args.destCurrency)
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentRateHistoryBinding {
        return FragmentRateHistoryBinding.inflate(inflater, container, attachToParent)
    }
}