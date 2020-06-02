package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Item
import java.util.*

@Keep
@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String?,
    val link: String?,
    val description: String?,
    val image: String?
) {
    constructor(channel: Channel): this(
        channel.id ?: UUID.randomUUID().toString(),
        channel.title ?: "?",
        channel.link,
        channel.description,
        channel.image
    )

    fun parse(items: List<ItemEntity>): Channel {
        val parsedFeeds = mutableListOf<Item>()
        for(feed in items) {
            parsedFeeds.add(feed.parse())
        }
        return Channel(id, title, link, description, image, parsedFeeds)
    }

    override fun equals(other: Any?): Boolean {
        return other is ItemEntity
                && id == other.id
                && title == other.title
                && link == other.link
                && description == other.description
                && image == other.image
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }
}
