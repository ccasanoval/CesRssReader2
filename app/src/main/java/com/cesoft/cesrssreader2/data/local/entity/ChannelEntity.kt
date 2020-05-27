package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Feed
import java.util.*

@Keep // Proguard
@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String?,
    val link: String?,
    val description: String?,
    val image: String? = null,
    val created: Long = System.currentTimeMillis()
    //val articles: MutableList<Feed> = mutableListOf()
) {
    constructor(channel: Channel): this(
        channel.id ?: UUID.randomUUID().toString(),
        channel.title ?: "?",
        channel.link,
        channel.description,
        channel.image,
        System.currentTimeMillis()
    )

    fun parse(feeds: List<FeedEntity>): Channel {
        val parsedFeeds = mutableListOf<Feed>()
        for(feed in feeds) {
            parsedFeeds.add(feed.parse())
        }
        return Channel(id, title, link, description, image, parsedFeeds)
    }
}
/**
package com.prof.rssparser
data class Channel(
    val title: String? = null,
    val link: String? = null,
    val description: String? = null,
    val image: Image? = null,
    val articles: MutableList<Article> = mutableListOf()
)*/