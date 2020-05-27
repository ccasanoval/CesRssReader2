package com.cesoft.cesrssreader2.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.cesoft.cesrssreader2.data.entity.Feed
import com.cesoft.cesrssreader2.data.entity.Channel

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

    fun parse(channel: com.prof.rssparser.Channel): Channel {
        val feeds = mutableListOf<Feed>()
        for(article in channel.articles) {
            var categories = ""
            for(category in article.categories) {
                categories += "$category "
            }
            feeds.add(Feed(
                null,
                article.guid,
                article.title,
                article.author,
                article.link,
                article.pubDate,
                article.description,
                article.content,
                article.image,
                categories
            ))
        }
        //android.util.Log.e("Util", "parse---------------------------------------"+channel.image?.title+" : "+channel.image?.link+" : "+channel.image?.url+" : "+channel.image?.description)
        return Channel(
            null,
            channel.title,
            channel.link,
            channel.description,
            channel.image?.toString(),
            feeds
        )
    }
}