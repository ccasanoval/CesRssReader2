package com.cesoft.cesrssreader2.ui.feedlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.Repo
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FeedlistViewModel : ViewModel(), KoinComponent {

    companion object {
        private val TAG: String = FeedlistViewModel::class.simpleName!!
    }

    private val repo: Repo by inject()

    private val _snackbar = MutableLiveData<Any?>()
    val snackbar: LiveData<Any?>
        get() = _snackbar

    private val _channel = MutableLiveData<Channel>()
    val channel: LiveData<Channel>
        get() = _channel

    private val _rssUrlList = MutableLiveData<List<String>>()
    val rssUrlList: LiveData<List<String>>
        get() = _rssUrlList

    private val _rssUrl = MutableLiveData<String>()
    val rssUrl: LiveData<String>
        get() = _rssUrl

    init {
        GlobalScope.launch(Dispatchers.Main) {
            //delay(15000)
            val rssList = fetchRssUrlList()
            if(rssList.isNotEmpty()) {
                _rssUrl.postValue(rssList[0])
                fetchFeed(rssList[0])
            }
            _rssUrlList.postValue(rssList)
        }
    }

    fun onSnackbarShowed() { _snackbar.value = null }

    fun fetchFeed(url: String) {
        GlobalScope.launch(Dispatchers.Main) {
            //delay(15000)
            try {
                val channel = repo.fetchChannel(url)
                channel?.let {
                    _channel.postValue(it)
                    _rssUrlList.postValue(fetchRssUrlList())
                } ?: run {
                    _snackbar.postValue(R.string.alert_message)
                    _channel.postValue(Channel.EMPTY)
                }
            }
            catch(e: Exception) {
                Log.e(TAG, "fetchFeed:e:",e)
                _snackbar.postValue(e.localizedMessage)
                _channel.postValue(Channel.EMPTY)
            }
        }
    }

    private suspend fun fetchRssUrlList(): List<String> {
        val list0 = mutableListOf(
            "https://www.xatakandroid.com/tag/feeds/rss2.xml",
            "https://www.nasa.gov/rss/dyn/breaking_news.rss",
            "https://www.androidauthority.com/feed"
        )
        val list = repo.fetchRssUrls()
        return (list + list0).distinct()
    }

}
