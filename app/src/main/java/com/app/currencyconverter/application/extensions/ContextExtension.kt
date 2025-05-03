package com.app.currencyconverter.application.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.app.currencyconverter.R

fun Context.displayToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.displayErrorDialog(message: String?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(text = message)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
            icon(R.mipmap.ic_launcher)

        }
}

fun Context.displayErrorDialog(message: Int?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(res = message)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
            icon(R.mipmap.ic_launcher)
        }
}