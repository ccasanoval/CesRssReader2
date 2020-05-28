package com.cesoft.cesrssreader2.data.remote.entity

import androidx.annotation.Keep
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Feed
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

//https://github.com/Tickaroo/tikxml/blob/master/docs/AnnotatingModelClasses.md
@Keep
@Xml(name = "rss")
data class RssEntity(
    @field:Attribute
    var version: String?,
    @field:Element
    var channel: ChannelEntity
) {
    constructor(): this("2", ChannelEntity())

    fun parse() = Channel(
        null,
        channel.title,
        channel.link,
        channel.description,
        channel.image,
        parseItems())

    private fun parseItems(): MutableList<Feed> =
        channel.items?.filterNotNull().let { items ->
            MutableList(items!!.size) { i ->
                items[i].parse()
            }
        } ?: mutableListOf<Feed>()
}