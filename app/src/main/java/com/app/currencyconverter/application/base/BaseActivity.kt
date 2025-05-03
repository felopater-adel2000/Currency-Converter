package com.app.currencyconverter.application.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.currencyconverter.application.extensions.displayErrorDialog
import com.app.currencyconverter.application.extensions.displayToast
import com.app.currencyconverter.application.utils.DataStateChangeListener
import com.app.currencyconverter.domain.state.ResponseType
import com.app.currencyconverter.domain.state.StateError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseActivity : AppCompatActivity(), DataStateChangeListener {
    private val TAG = "BaseActivity"

    override fun onErrorStateChange(stateError: StateError) {
        lifecycleScope.launch(Dispatchers.Main) {
            handleStateErrorEvent(stateError)
        }
    }

    private fun handleStateErrorEvent(stateError: StateError) {
        when (stateError.response?.responseType) {
            is ResponseType.Dialog -> {
                stateError.response.message?.let { message -> displayErrorDialog(message) }

            }

            is ResponseType.Toast -> {
                stateError.response.message?.let { message -> displayToast(message) }
            }

            is ResponseType.None -> {
                Log.e(TAG, "handleStateErrorEvent: ${stateError.response.message}")
            }

            is ResponseType.SnakeBar -> {}

            else -> {}
        }
    }
}