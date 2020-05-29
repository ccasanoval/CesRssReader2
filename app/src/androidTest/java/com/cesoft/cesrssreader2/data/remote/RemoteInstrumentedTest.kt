package com.cesoft.cesrssreader2.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

////////////////////////////////////////////////////////////////////////////////////////////////////
//
@RunWith(AndroidJUnit4::class)
class RemoteInstrumentedTest {

    //
    /// Notice that RSS will change in time... This is for just test once at a time manually
    //  https://validator.w3.org/feed/check.cgi?url=https%3A%2F%2Fwww.androidauthority.com%2Ffeed%2F

    private lateinit var serv: RssServiceImpl

    @Before
    fun init() {
        serv = RssServiceImpl()
    }

    @After
    fun end() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun remoteInstrumentedTestA() = runBlocking {
        //https://www.androidauthority.com/feed/
        val rssEntity = serv.rss("https://www.androidauthority.com/feed")

        Assert.assertEquals("2.0", rssEntity.version)
        Assert.assertEquals("Android Authority", rssEntity.channel.title)
        Assert.assertEquals("https://www.androidauthority.com", rssEntity.channel.link)
        Assert.assertEquals("Android News, Reviews, How To", rssEntity.channel.description)
        Assert.assertEquals("Thu, 28 May 2020 10:05:13 +0000", rssEntity.channel.lastBuildDate)

        Assert.assertEquals(10, rssEntity.channel.items?.size)
        //Assert.assertEquals("https://www.androidauthority.com/?p=123731", rssEntity.channel.items?.get(0)?.guid)
        //Assert.assertEquals("https://www.androidauthority.com/google-pixel-4a-1047299/", rssEntity.channel.items?.get(0)?.link)
        Assert.assertEquals("Thu, 28 May 2020 10:05:13 +0000", rssEntity.channel.items?.get(0)?.pubDate)
        Assert.assertEquals("Samsung Galaxy Note 20 series could get a massive chipset upgrade", rssEntity.channel.items?.get(0)?.title)
        Assert.assertEquals("https://cdn57.androidauthority.net/wp-content/uploads/2020/05/Samsung-Galaxy-Note-20-Renders-2-500x260.jpg", rssEntity.channel.items?.get(0)?.image)

        Assert.assertEquals(3, rssEntity.channel.items?.get(0)?.categories?.size)
        Assert.assertEquals("News", rssEntity.channel.items?.get(0)?.categories?.get(0)?.category)
        Assert.assertEquals("Samsung Exynos", rssEntity.channel.items?.get(0)?.categories?.get(2)?.category)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun remoteInstrumentedTestB() = runBlocking {
        //https://www.youtube.com/feeds/videos.xml?user=UFC
        //val rssEntity = serv.rss("https://www.youtube.com/", "feeds/videos.xml", mapOf(Pair("user","UFC")))

        //https://www.nasa.gov/rss/dyn/breaking_news.rss
        val rssEntity = serv.rss("https://www.nasa.gov/rss/dyn/breaking_news.rss")

        Assert.assertEquals("2.0", rssEntity.version)
        Assert.assertEquals("NASA Breaking News", rssEntity.channel.title)
        Assert.assertEquals("http://www.nasa.gov/", rssEntity.channel.link)
        Assert.assertEquals("A RSS news feed containing the latest NASA news articles and press releases.", rssEntity.channel.description)
        Assert.assertEquals("", rssEntity.channel.lastBuildDate)

        Assert.assertEquals(10, rssEntity.channel.items?.size)
        Assert.assertEquals("NASA Science to Hold Virtual Community Town Hall Meeting", rssEntity.channel.items?.get(0)?.title)
        Assert.assertEquals("http://www.nasa.gov/press-release/nasa-science-to-hold-virtual-community-town-hall-meeting-0", rssEntity.channel.items?.get(0)?.guid)
        Assert.assertEquals("http://www.nasa.gov/press-release/nasa-science-to-hold-virtual-community-town-hall-meeting-0", rssEntity.channel.items?.get(0)?.link)
        Assert.assertEquals("Fri, 22 May 2020 12:11 EDT", rssEntity.channel.items?.get(0)?.pubDate)
        Assert.assertEquals("", rssEntity.channel.items?.get(0)?.image)
        //TODO: alternatives for img
        //<enclosure url="http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/smdtownhall.jpg?itok=A50p-eTJ" length="315521" type="image/jpeg"/>

        Assert.assertEquals(0, rssEntity.channel.items?.get(0)?.categories?.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun remoteInstrumentedTestC() = runBlocking {
        //http://www.xatakandroid.com/tag/feeds/rss2.xml
        val rssEntity = serv.rss("http://www.xatakandroid.com/tag/feeds/rss2.xml")

        Assert.assertEquals("2.0", rssEntity.version)
        Assert.assertEquals("Magazine - feeds", rssEntity.channel.title)
        Assert.assertEquals("https://www.xatakandroid.com", rssEntity.channel.link)
        Assert.assertEquals("Publicación de noticias sobre gadgets y tecnología. Últimas tecnologías en electrónica de consumo y novedades tecnológicas en móviles, tablets, informática, etc", rssEntity.channel.description)
        Assert.assertEquals("", rssEntity.channel.lastBuildDate)

        Assert.assertEquals(3, rssEntity.channel.items?.size)
        Assert.assertEquals("Los planes de feedly Pro ya están disponibles para todo el mundo", rssEntity.channel.items?.get(0)?.title)
        Assert.assertEquals("https://www.xatakandroid.com/productividad-herramientas/los-planes-de-feedly-pro-ya-estan-disponibles-para-todo-el-mundo", rssEntity.channel.items?.get(0)?.guid)
        Assert.assertEquals("https://www.xatakandroid.com/productividad-herramientas/los-planes-de-feedly-pro-ya-estan-disponibles-para-todo-el-mundo", rssEntity.channel.items?.get(0)?.link)
        Assert.assertEquals("Mon, 26 Aug 2013 16:30:13 +0000", rssEntity.channel.items?.get(0)?.pubDate)
        Assert.assertEquals("", rssEntity.channel.items?.get(0)?.image)

        Assert.assertEquals(0, rssEntity.channel.items?.get(0)?.categories?.size)
    }
}
