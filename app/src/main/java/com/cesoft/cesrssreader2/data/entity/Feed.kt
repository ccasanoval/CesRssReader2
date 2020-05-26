package com.cesoft.cesrssreader2.data.entity

data class Feed(
    val id: String?,
    val guid: String?,
    val title: String?,
    val author: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    var content: String?,
    var image: String?,
    var categories: String?
)