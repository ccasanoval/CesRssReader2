package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Feed
import java.util.*

@Keep // Proguard
@Entity(tableName = "feeds", indices = [Index(value = ["id"]), Index(value = ["title"])])
data class FeedEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val guid: String?,
    val title: String?,
    val author: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    var content: String?,
    var image: String?,
    var categories: String?
) {
    constructor(feed: Feed): this(
        UUID.randomUUID().toString(),
        feed.guid,
        feed.title,
        feed.author,
        feed.link,
        feed.pubDate,
        feed.description,
        feed.content,
        feed.image,
        feed.categories
    )

    fun parse() = Feed(
        id,
        guid,
        title,
        author,
        link,
        pubDate,
        description,
        content,
        image,
        categories
    )
}
/**
package com.prof.rssparser
data class Article(
    var guid: String? = null,
    var title: String? = null,
    var author: String? = null,
    var link: String? = null,
    var pubDate: String? = null,
    var description: String? = null,
    var content: String? = null,
    var image: String? = null,
    private var _categories: MutableList<String> = mutableListOf()
)*/