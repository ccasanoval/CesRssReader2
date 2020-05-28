package com.cesoft.cesrssreader2.data

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
        private const val MAX_TIME = 5*60*1000//TODO: User settings...
    }

    val util: Util by inject()

    private suspend fun fetchLocalFeeds()
            = dao.channel()?.parse(dao.items())
    private suspend fun fetchRemoteFeeds(url: String)
            = service.rss(url).parse()
    private suspend fun updateLocalFeeds(url: String, channel: Channel): Channel {
        val channelParsed = ChannelEntity(channel)
        val feedsParsed = mutableListOf<ItemEntity>()
        for(feed in channel.feeds) {
            feedsParsed.add(ItemEntity(feed))
        }
        dao.addRssUrl(RssUrlEntity(url))
        dao.updateChannel(channelParsed)
        dao.deleteItems()
        dao.updateItems(feedsParsed)
        return channel
    }

    private enum class DataSource { NONE, LOCAL, REMOTE }
    private suspend fun localOrRemote(): DataSource {
        val channel = dao.channel()
        val db = channel != null && dao.items().isNotEmpty()

        return if(util.isOnline()) {
            if(db) {
                if(MAX_TIME > System.currentTimeMillis() - channel!!.created)
                    DataSource.LOCAL
                else
                    DataSource.REMOTE
            } else
                DataSource.REMOTE
        } else if(db) {
            DataSource.LOCAL
        } else
            DataSource.NONE
    }

    //TODO: use channel.lastBuildDate in the algorithm ?
    suspend fun fetchFeeds(url: String): Channel? {
        return when(localOrRemote()) {
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
}