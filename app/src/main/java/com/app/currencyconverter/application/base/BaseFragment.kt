package com.app.currencyconverter.application.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, P : ViewModel> : Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val viewModel: P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_binding == null) _binding = getBinding(inflater, container, false)
        return binding.root
    }

    abstract fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean,
    ): T?

}