package com.cesoft.cesrssreader2.data.entity

data class Channel(
    val id: String?,
    val title: String?,
    val link: String?,
    val description: String?,
    val image: String? = null,
    val feeds: MutableList<Feed>
) {
    companion object {
        val EMPTY = Channel(null, null, null, null, null, mutableListOf<Feed>())
    }
}
