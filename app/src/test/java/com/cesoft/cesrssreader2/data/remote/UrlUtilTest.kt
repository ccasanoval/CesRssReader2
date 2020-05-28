package com.cesoft.cesrssreader2.data.remote

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UrlUtilTest {

    @Before
    fun init() {
    }

    @Test
    fun testA() {
        //val rssEntity = serv.rss("https://www.androidauthority.com/", "feed")
        //val rssEntity = serv.rss("https://www.youtube.com/", "feeds/videos.xml", mapOf(Pair("user","UFC")))
        //val rssEntity = serv.rss("https://www.nasa.gov/","rss/dyn/breaking_news.rss")

        var url = UrlUtil.parse("https://www.androidauthority.com/feed")
        Assert.assertEquals("https://www.androidauthority.com/", url.domain)
        Assert.assertEquals("feed", url.path)
        Assert.assertEquals(0, url.queries.size)

        url = UrlUtil.parse("https://www.youtube.com/feeds/videos.xml?user=UFC")
        Assert.assertEquals("https://www.youtube.com/", url.domain)
        Assert.assertEquals("feeds/videos.xml", url.path)
        Assert.assertEquals(1, url.queries.size)
        Assert.assertEquals(mapOf(Pair("user","UFC")), url.queries.toMap())

        url = UrlUtil.parse("https://www.androidauthority.com/feed/a.php?tralara=trassss&tralari=pinpon")
        Assert.assertEquals("https://www.androidauthority.com/", url.domain)
        Assert.assertEquals("feed/a.php", url.path)
        Assert.assertEquals(2, url.queries.size)
    }
}