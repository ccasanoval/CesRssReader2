package com.cesoft.cesrssreader2.data.remote

import com.cesoft.cesrssreader2.data.remote.entity.RssEntity
import retrofit2.http.*

interface RssService {
    @GET("{path}")
    suspend fun fetchRss(@Path("path", encoded=true) path: String="", @QueryMap query: Map<String, String>): RssEntity
}