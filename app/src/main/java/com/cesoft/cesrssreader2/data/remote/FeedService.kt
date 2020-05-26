package com.cesoft.cesrssreader2.data.remote

import com.prof.rssparser.Parser

class FeedService(val util: Util) {
    private val parser = Parser()
    suspend fun getFeeds(url: String) = util.parse(parser.getChannel(url))
}