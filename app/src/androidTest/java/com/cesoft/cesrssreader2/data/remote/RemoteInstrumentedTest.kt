package com.cesoft.cesrssreader2.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteInstrumentedTest {

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val util = Util(context)
    private val serv = FeedService(util)

    @Before
    fun init() {

    }

    @After
    fun end() {

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testA() = runBlocking {
        val channel = serv.getFeeds("https://www.androidauthority.com/feed")//context.getString(com.cesoft.cesrssreader2.R.string.default_url))//https://www.androidauthority.com/feed
        Assert.assertEquals("Link is wrong","https://www.androidauthority.com", channel.link)
        Assert.assertEquals("Title is wrong","Android Authority", channel.title)
        Assert.assertEquals("# of articles is wrong", 10, channel.feeds.size)
    }
}
