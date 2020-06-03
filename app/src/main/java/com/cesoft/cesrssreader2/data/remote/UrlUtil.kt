package com.cesoft.cesrssreader2.data.remote

import java.net.URI
import java.net.URLDecoder

data class UrlUtil(
    val domain: String,
    val path: String,
    val queries: Map<String, String>
) {
    companion object {
        fun parse(url: String): UrlUtil {
            val uri = URI(url)

            val domain = "${uri.scheme}://${uri.host}/"
            val path = if(uri.path.isEmpty() || uri.path[0]!='/') uri.path else uri.path.substring(1)
            val query = uri.query

            val queries = mutableMapOf<String, String>()
            val pairs = query?.split("&")?.toTypedArray()
            pairs?.let {
                for(pair in it) {
                    val idx = pair.indexOf("=")
                    val key = URLDecoder.decode(pair.substring(0, idx), "UTF-8")
                    val value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
                    queries[key] = value
                }
            }

            return UrlUtil(
                domain,
                path,
                queries
            )
        }
    }

}