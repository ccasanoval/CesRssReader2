package com.cesoft.cesrssreader2.data.remote

import android.util.Log
import com.cesoft.cesrssreader2.data.remote.entity.RssEntity
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RssServiceImpl {

    companion object {
        private val TAG: String = RssServiceImpl::class.simpleName!!
    }

    private val rssConverter: TikXmlConverterFactory
    private val okHttpClient: OkHttpClient

    init {
        val rssParser = TikXml.Builder()
            //.exceptionOnUnreadXml(false)
            //.addTypeConverter<Any>(String::class.java, HtmlEscapeStringConverter())
            .build()
        rssConverter = TikXmlConverterFactory.create(rssParser)

        //Check also https://github.com/ihsanbal/LoggingInterceptor
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e(TAG, "HttpLoggingInterceptor: $message")
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun getService(url: String): RssService = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(rssConverter)
            .build()
            .create(RssService::class.java)

    suspend fun rss(url: String): RssEntity {
        val urlParsed = UrlUtil.parse(url)
        return getService(urlParsed.domain).fetchRss(urlParsed.path, urlParsed.queries)
    }
}