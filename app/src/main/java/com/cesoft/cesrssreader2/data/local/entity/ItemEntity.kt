package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cesoft.cesrssreader2.data.entity.Feed
import java.util.*

@Keep
@Entity(tableName = "items", indices = [Index(value = ["id"]), Index(value = ["title"])])
data class ItemEntity(
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
        feed.id ?: UUID.randomUUID().toString(),
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

    /*override fun equals(other: Any?): Boolean {
        return other is ItemEntity
                && id == other.id
                && guid == other.guid
                && title == other.title
                && author == other.author
                && link == other.link
                && pubDate == other.pubDate
                && description == other.description
                && content == other.content
                && image == other.image
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (guid?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + (pubDate?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (categories?.hashCode() ?: 0)
        return result
    }*/
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