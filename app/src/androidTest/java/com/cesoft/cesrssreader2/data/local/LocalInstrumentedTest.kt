package com.cesoft.cesrssreader2.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cesoft.cesrssreader2.data.entity.Feed
import com.cesoft.cesrssreader2.data.local.dao.RssDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class LocalInstrumentedTest {

    private val channelId: String = UUID.randomUUID().toString()
    private val channelTitle: String = "Channel Title"
    private val channelLink: String = "Channel Link"
    private val channelDescription: String = "Channel Description"
    private val channelImage: String = "Channel Image"

    private val feedId = UUID.randomUUID().toString()
    private val feedGuid: String = "guid"
    private val feedTitle: String = "title"
    private val feedAuthor: String = "author"
    private val feedLink: String = "link"
    private val feedPubDate: String = "pubDate"
    private val feedDescription: String = "description"
    private var feedContent: String = "content"
    private var feedImage: String = "image"
    private var feedCategories: String = "categories"

    private val context = InstrumentationRegistry.getInstrumentation().context
    private lateinit var db: RssDb
    private lateinit var dao: RssDao

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(context, RssDb::class.java).build()
        dao = db.feedDao
    }

    @After
    @Throws(IOException::class)
    fun end() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testA() = runBlocking {

        var channelEntity = dao.channel()
        Assert.assertEquals("", ChannelEntity@null, channelEntity)
        var feedsEntiy = dao.items()
        Assert.assertEquals("", listOf<ItemEntity>(), feedsEntiy)

        channelEntity = ChannelEntity(channelId, channelTitle, channelLink, channelDescription, channelImage)
       // val time = System.currentTimeMillis() - channelEntity.created
        //Assert.assertTrue(time in 0..99)

        val feeds = List(3) { i ->
            Feed("id$i", "$feedGuid$i", "$feedTitle$i", "$feedAuthor$i",
                "$feedLink$i", "$feedPubDate$i", "$feedDescription$i",
                "$feedContent$i", "$feedImage$i", "$feedCategories$i")
        }
        feedsEntiy = mutableListOf()
        for(feed in feeds) {
            feedsEntiy.add(ItemEntity(feed))
        }

        dao.updateChannel(channelEntity)
        dao.updateItems(feedsEntiy)

        val channelEntity2 = dao.channel()
        val feedEntity2 = dao.items()

        Assert.assertEquals(channelEntity, channelEntity2)
        Assert.assertEquals(feedsEntiy, feedEntity2)
    }
}
