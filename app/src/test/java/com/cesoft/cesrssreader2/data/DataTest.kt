package com.cesoft.cesrssreader2.data

import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Feed
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*


@RunWith(JUnit4::class)
class DataTest {

    private val channelId: String = UUID.randomUUID().toString()
    private val channelTitle: String = "Channel Title"
    private val channelLink: String = "Channel Link"
    private val channelDescription: String = "Channel Description"
    private val channelImage: String = "Channel Image"

    private val feedGuid: String = "guid"
    private val feedTitle: String = "title"
    private val feedAuthor: String = "author"
    private val feedLink: String = "link"
    private val feedPubDate: String = "pubDate"
    private val feedDescription: String = "description"
    private var feedContent: String = "content"
    private var feedImage: String = "image"
    private var feedCategories: String = "categories"


    private val feeds = MutableList(3) { i ->
        Feed("id$i", "$feedGuid$i", "$feedTitle$i", "$feedAuthor$i",
            "$feedLink$i", "$feedPubDate$i", "$feedDescription$i",
            "$feedContent$i", "$feedImage$i", "$feedCategories$i")
    }
    private val channel = Channel(channelId, channelTitle, channelLink, channelDescription, channelImage, feeds)

    private val feedsEntity = MutableList(3) { i ->
        ItemEntity("id$i", "$feedGuid$i", "$feedTitle$i", "$feedAuthor$i",
            "$feedLink$i", "$feedPubDate$i", "$feedDescription$i",
            "$feedContent$i", "$feedImage$i", "$feedCategories$i")
    }
    private val channelEntity = ChannelEntity(channelId, channelTitle, channelLink, channelDescription, channelImage)

    @Before
    fun init() {

    }

    @After
    fun end() {

    }

    @Test
    fun testA() {
        val channel2 = channelEntity.parse(feedsEntity)
        Assert.assertEquals(channel, channel2)
        Assert.assertEquals(channel.feeds, channel2.feeds)
    }

    @Test
    fun testB() {
        val channelEntity2 = ChannelEntity(channel)
        //channelEntity2.id = channel.id
        Assert.assertEquals(channelEntity, channelEntity2)
        Assert.assertEquals(channelEntity.id, channelEntity2.id)
        Assert.assertEquals(channelEntity.description, channelEntity2.description)
        Assert.assertEquals(channelEntity.image, channelEntity2.image)
        Assert.assertEquals(channelEntity.link, channelEntity2.link)
        Assert.assertEquals(channelEntity.title, channelEntity2.title)

        val feedsEntity2 = mutableListOf<ItemEntity>()
        for(feed in feeds) {
            feedsEntity2.add(ItemEntity(feed))
        }
        Assert.assertEquals(feedsEntity, feedsEntity2)
    }
}