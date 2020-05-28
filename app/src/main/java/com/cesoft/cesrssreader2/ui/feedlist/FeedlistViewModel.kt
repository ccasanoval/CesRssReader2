package com.cesoft.cesrssreader2.ui.feedlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.Repo
import com.cesoft.cesrssreader2.data.entity.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class FeedlistViewModel : ViewModel(), KoinComponent {

    companion object {
        private val TAG: String = FeedlistViewModel::class.simpleName!!
    }

    private val repo: Repo by inject()

    private val _snackbar = MutableLiveData<Any?>()
    val snackbar: LiveData<Any?>
        get() = _snackbar
    private val _snackbarString = MutableLiveData<String?>()
    val snackbarString: LiveData<String?>
        get() = _snackbarString

    private val _feedlist = MutableLiveData<Channel>()
    val feedlist: LiveData<Channel>
        get() = _feedlist

    fun onSnackbarShowed() {
        _snackbar.value = null
    }

    fun fetchFeed(url: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val feeds = repo.fetchFeeds(url)
                if (feeds != null) {
                    _feedlist.postValue(feeds!!)
                } else {
                    _snackbar.postValue(R.string.alert_message)
                    _feedlist.postValue(Channel.EMPTY)
                }
            }
            catch(e: Exception) {
                Log.e(TAG, "fetchFeed:e:-----------------------------------------------",e)
                _snackbar.postValue(e.localizedMessage)
            }
        }
    }
}
