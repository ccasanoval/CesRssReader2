package com.cesoft.cesrssreader2.data

import android.util.Log
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.local.dao.RssDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import com.cesoft.cesrssreader2.data.local.entity.RssUrlEntity
import com.cesoft.cesrssreader2.data.remote.RssServiceImpl
import com.cesoft.cesrssreader2.data.remote.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

class Repo(private val dao: RssDao,
           private val service: RssServiceImpl,
           private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default)
    : KoinComponent {

    companion object {
        private val TAG: String = Repo::class.simpleName!!
        private const val MAX_TIME = 15*60*1000//TODO: User settings...
    }

    val util: Util by inject()

    private suspend fun fetchLocalFeeds()
            = dao.channel()?.parse(dao.items())
    private suspend fun fetchRemoteFeeds(url: String)
            = service.rss(url).parse()
    private suspend fun updateLocalFeeds(url: String, channel: Channel): Channel {
        val channelParsed = ChannelEntity(channel)
        val feedsParsed = mutableListOf<ItemEntity>()
        for(feed in channel.items) {
            feedsParsed.add(ItemEntity(feed))
        }
        dao.addRssUrl(RssUrlEntity(url))
        dao.updateChannel(channelParsed)
        dao.deleteItems()
        dao.updateItems(feedsParsed)
        return channel
    }

    //TODO: use channel.lastBuildDate in the algorithm ?
    private enum class DataSource { NONE, LOCAL, REMOTE }
    private suspend fun localOrRemote(url: String): DataSource {
        val rssUrlList = dao.rssUrls()
        if(rssUrlList.isEmpty() || rssUrlList[0].url != url) {
            return if(util.isOnline())
                DataSource.REMOTE
            else
                DataSource.NONE
        }

        val lastUpdate = rssUrlList[0].created
        val channel = dao.channel()
        val db = channel != null && dao.items().isNotEmpty()

        return if(util.isOnline()) {
            if(db) {
                if(System.currentTimeMillis() - lastUpdate > MAX_TIME)
                    DataSource.REMOTE
                else
                    DataSource.LOCAL
            } else
                DataSource.REMOTE
        } else if(db) {
            DataSource.LOCAL
        } else
            DataSource.NONE
    }

    suspend fun fetchChannel(url: String): Channel? {
        return when(localOrRemote(url)) {
            DataSource.LOCAL -> {
                fetchLocalFeeds()
            }
            DataSource.REMOTE -> {
                updateLocalFeeds(url, fetchRemoteFeeds(url))
            }
            DataSource.NONE -> {
                null
            }
        }
    }

    suspend fun fetchRssUrls(): List<String> {
        val urlList = dao.rssUrls()
        return List(urlList.size) { i -> urlList[i].url }
    }
}