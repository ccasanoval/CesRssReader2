package com.cesoft.cesrssreader2.ui.feedlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.Repo
import com.cesoft.cesrssreader2.data.entity.Channel
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class FeedlistViewModel : ViewModel(), KoinComponent {

    companion object {
        private val TAG: String = FeedlistViewModel::class.simpleName!!
    }

    private val _repo: Repo by inject()

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

    private var _job: Job? = null
    private var _isWorking = MutableLiveData<Boolean>()
    val isWorking: LiveData<Boolean>
        get() = _isWorking

    init {
        _job = GlobalScope.launch {
            try {
                val rssList = fetchRssUrlList()
                _rssUrlList.postValue(rssList)
                if (rssList.isNotEmpty()) {
                    _rssUrl.postValue(rssList[0])
                    fetchFeed(rssList[0])
                }
            }
            catch(e: CancellationException) {
                _snackbar.postValue(R.string.alert_cancelled)
                _channel.postValue(Channel.EMPTY)
            }
            finally {
                //withContext(NonCancellable) {
                _isWorking.postValue(false)
            }
        }
        _isWorking.postValue(_job?.isActive)
    }

    fun onSnackbarShowed() { _snackbar.value = null }

    fun cancel() {
        _job?.cancel()
        _isWorking.postValue(false)
    }
    fun fetchFeed(url: String) {
        _job = GlobalScope.launch {
            try {
                if(!isActive)throw CancellationException()
                val channel = _repo.fetchChannel(url)
                channel?.let {
                    _channel.postValue(it)
                    _rssUrlList.postValue(fetchRssUrlList())
                    _rssUrl.postValue(url)
                } ?: run {
                    _snackbar.postValue(R.string.alert_no_inet)
                    _channel.postValue(Channel.EMPTY)
                }
            }
            catch(e: CancellationException) {
                _snackbar.postValue(R.string.alert_cancelled)
                _channel.postValue(Channel.EMPTY)
            }
            catch(e: Exception) {
                Log.e(TAG, "fetchFeed:e:", e)
                _snackbar.postValue("Error: ${e.localizedMessage}")
                _channel.postValue(Channel.EMPTY)
                delay(5000L)
                fetchFeed(url)
            }
            finally {
                _isWorking.postValue(false)
            }
        }
        _isWorking.postValue(_job?.isActive)
    }

    private suspend fun fetchRssUrlList(): List<String> {
        val list0 = mutableListOf(
            "https://www.androidauthority.com/feed",
            "https://www.xatakandroid.com/tag/feeds/rss2.xml",
            "https://www.nasa.gov/rss/dyn/breaking_news.rss"
        )
        val list = _repo.fetchRssUrls()
        return (list + list0).distinct()
    }

}
