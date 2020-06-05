package com.cesoft.cesrssreader2.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

/// Hide the keyboard
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/// Parsing utilities
fun String.toHtml(): String {
   return parseAsHtml().toString().replace(0xFFFC.toChar(), ' ').trim()
}
fun Long.toDate(alternativeDate: String=""): String {
    return try {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)
    }
    catch(e: Exception) {
        alternativeDate
    }
}
fun String.toDate(): String {
    val formats = listOf(
        "EEE, dd MMM yyyy HH:mm:ss Z",
        "EEE, dd MMM yyyy HH:mm Z",
        "EEE, d MMM yyyy HH:mm:ss Z",
        "EEE, d MMM yyyy HH:mm Z",
        "EEE dd MMM yyyy HH:mm:ss Z",
        "EEE dd MMM yyyy HH:mm Z",
        "EEE d MMM yyyy HH:mm:ss Z",
        "EEE d MMM yyyy HH:mm Z",
        "EE, MMM dd HH:mm:ss z yyyy",
        "EE MMM dd HH:mm:ss z yyyy"
    )
    for(format in formats) {
        try {
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            val date = sdf.parse(this)
            if (date != null) {
                return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
            }
        }
        catch(e: Exception) {
            //Log.e(TAG, "ViewHolder:bind:e:", e)
        }
    }
    return ""
}

