package com.cesoft.cesrssreader2.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    val parsed = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this.trim(), Html.FROM_HTML_MODE_COMPACT).toString()
    else
        Html.fromHtml(this.trim()).toString()
    if(parsed.isEmpty()) return ""
    return if(parsed[0].isLetterOrDigit())
        parsed.trim()
    else
        parsed.substring(1).trim()
}
fun String.toDate(): String {
    val formats = listOf(
        "EEE, dd MMM yyyy HH:mm Z",
        "EE MMM dd HH:mm:ss z yyyy",
        "EEE, d MMM yyyy HH:mm:ss Z"
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

