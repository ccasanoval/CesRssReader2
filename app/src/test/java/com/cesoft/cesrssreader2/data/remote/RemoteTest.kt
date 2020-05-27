package com.cesoft.cesrssreader2.data.remote

import android.app.Application
import android.content.Context
import com.cesoft.cesrssreader2.data.entity.Channel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


//https://medium.com/@hanru.yeh/unit-test-retrofit-and-mockwebserver-a3e4e81fd2a2

@RunWith(JUnit4::class)
class RemoteTest {

    @Mock
    private lateinit var context: Application
    private lateinit var util: Util
    private lateinit var serv: FeedService
    private lateinit var channel: com.prof.rssparser.Channel

    private val title: String? = "channel_title"
    private val link: String? = "channel_link"
    private val description: String? = "channel_description"
    private val image: com.prof.rssparser.Image? = com.prof.rssparser.Image("img_title","img_url", "img_link", "img_description")
    private val articles: MutableList<com.prof.rssparser.Article> = MutableList(3) { i ->
        com.prof.rssparser.Article(
            "guid$i",
            "title$i",
            "author$i",
            "link$i",
            "pubDate$i",
            "description$i",
            "content$i",
            "image$i",
            MutableList(3) { j -> "category $i $j"})
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        util = Util(context)
        serv = FeedService(util)
        channel = com.prof.rssparser.Channel(title, link, description, image, articles)
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        //Mockito.`when`(context.resources).thenReturn(Mockito.mock(Resources::class.java))
        //Mockito.`when`(context.resources.getStringArray(R.array.leagues_id)).thenReturn(testArrayLeagueId)
    }

    @After
    fun end() {

    }

    @Test
    fun testA() {
        val channelParsed = util.parse(channel)
        Assert.assertTrue(channelParsed is com.cesoft.cesrssreader2.data.entity.Channel)
        Assert.assertNotEquals("Objects are equal", channel, channelParsed)

        Assert.assertEquals("link is different", channel.link, channelParsed.link)
        Assert.assertEquals("title is different", channel.title, channelParsed.title)
        Assert.assertEquals("description is different", channel.description, channelParsed.description)
        Assert.assertEquals("Objects are equal", channel.image?.toString(), channelParsed.image)

        Assert.assertEquals("# of Feeds are different", channel.articles.size, channelParsed.feeds.size)
        for(i in 0 until channel.articles.size) {
            Assert.assertEquals("$i:Feed:author are different", channel.articles[i].author, channelParsed.feeds[i].author)
            Assert.assertEquals("$i:Feed:content are different", channel.articles[i].content, channelParsed.feeds[i].content)
            Assert.assertEquals("$i:Feed:description are different", channel.articles[i].description, channelParsed.feeds[i].description)
            Assert.assertEquals("$i:Feed:guid are different", channel.articles[i].guid, channelParsed.feeds[i].guid)
            Assert.assertEquals("$i:Feed:title are different", channel.articles[i].title, channelParsed.feeds[i].title)
            Assert.assertEquals("$i:Feed:image are different", channel.articles[i].image, channelParsed.feeds[i].image)
            Assert.assertEquals("$i:Feed:link are different", channel.articles[i].link, channelParsed.feeds[i].link)
            Assert.assertEquals("$i:Feed:pubDate are different", channel.articles[i].pubDate, channelParsed.feeds[i].pubDate)
            var categories = ""
            for(category in channel.articles[i].categories) categories += "$category "
            Assert.assertEquals("$i:Feed:categories are different", categories, channelParsed.feeds[i].categories)
        }

    }
}