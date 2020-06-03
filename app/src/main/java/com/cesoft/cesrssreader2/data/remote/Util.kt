package com.cesoft.cesrssreader2.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class Util(private val context: Context) {

    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        else {
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    companion object {
        fun getImgSrcFromHtml(htmlString: String): String {
            val doc: Document = Jsoup.parse(htmlString)
            val images: Elements = doc.select("img")
            for (el in images) {
                var img = el.attr("src")
                if(img.startsWith("//"))
                    img = "http:$img"
                return img
            }
            return ""
        }
    }
}