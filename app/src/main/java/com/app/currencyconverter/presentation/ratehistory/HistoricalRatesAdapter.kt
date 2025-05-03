package com.app.currencyconverter.presentation.ratehistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconverter.databinding.ItemHistoricalRateBinding
import com.app.currencyconverter.domain.model.HistoricalRateData
import java.text.SimpleDateFormat
import java.util.Locale

class HistoricalRatesAdapter : 
    ListAdapter<HistoricalRateData, HistoricalRatesAdapter.HistoricalRateViewHolder>(HistoricalRatesDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalRateViewHolder {
        val binding = ItemHistoricalRateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoricalRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalRateViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HistoricalRateViewHolder(
        private val binding: ItemHistoricalRateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: HistoricalRateData) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
            
            try {
                val date = dateFormat.parse(item.date)
                date?.let {
                    binding.tvDate.text = displayDateFormat.format(it)
                }
            } catch (e: Exception) {
                binding.tvDate.text = item.date
            }
            
            binding.tvCurrencyPair.text = "${item.fromCurrency}/${item.toCurrency}"
            binding.tvRate.text = item.rate.toString()
        }
    }
}

class HistoricalRatesDiffCallback : DiffUtil.ItemCallback<HistoricalRateData>() {
    override fun areItemsTheSame(oldItem: HistoricalRateData, newItem: HistoricalRateData): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: HistoricalRateData, newItem: HistoricalRateData): Boolean {
        return oldItem == newItem
    }
}