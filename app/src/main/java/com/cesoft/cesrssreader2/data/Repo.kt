package com.cesoft.cesrssreader2.data

import android.util.Log
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.local.dao.FeedDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.FeedEntity
import com.cesoft.cesrssreader2.data.remote.FeedService
import com.cesoft.cesrssreader2.data.remote.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

class Repo(private val feedDao: FeedDao,
           private val feedService: FeedService,
           private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default)
    : KoinComponent {

    companion object {
        private val TAG: String = Repo::class.simpleName!!
    }

    val util: Util by inject()

    private suspend fun fetchLocalFeeds() = feedDao.channel()?.parse(feedDao.feeds())
    private suspend fun fetchRemoteFeeds(url: String) = updateLocalFeeds(feedService.getFeeds(url))
    private suspend fun updateLocalFeeds(channel: Channel): Channel {
        val channelParsed = ChannelEntity(channel)
        val feedsParsed = mutableListOf<FeedEntity>()
        for(feed in channel.feeds) {
            feedsParsed.add(FeedEntity(feed))
        }
        feedDao.updateChannel(channelParsed)
        feedDao.deleteFeeds()
        feedDao.updateFeeds(feedsParsed)
        Log.e(TAG, "updateLocalFeeds--------------------------------------------------")
        return channel
    }

    //TODO: aclarar logica de actualizacion...
    //TODO: cache de libreria RSS-PARSE ??
    suspend fun fetchFeeds(url: String): Channel? {
        val channel = feedDao.channel()
        return if(util.isOnline()) {
            //TODO: check fecha del channel...
            channel?.let {
                val created = it.created
                val now = System.currentTimeMillis()
                if(now - created > 5*60*1000) {
                    val feeds = fetchRemoteFeeds(url)
                    updateLocalFeeds(feeds)
                    feeds
                }
                else
                    fetchLocalFeeds()
            } ?: run {
                fetchRemoteFeeds(url)
            }
        }
        else {
            channel?.let {
                fetchLocalFeeds()
            } ?: run {
                Log.e(TAG, "TODO: lanzar excepcion? porque no hay red ni datos!-------------------------------------------------------------------")
                null
            }
        }
    }
}