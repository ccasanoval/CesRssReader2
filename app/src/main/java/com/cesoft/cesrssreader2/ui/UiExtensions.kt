package com.cesoft.cesrssreader2.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

private fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
private fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}
private fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
