package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cesoft.cesrssreader2.data.entity.Item
import java.util.*

@Keep
@Entity(tableName = "items", indices = [Index(value = ["id"]), Index(value = ["title"])])
data class ItemEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val guid: String?,
    val title: String?,
    val author: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    var content: String?,
    var image: String?,
    var categories: String?
) {
    constructor(item: Item): this(
        item.id ?: UUID.randomUUID().toString(),
        item.guid,
        item.title,
        item.author,
        item.link,
        item.pubDate,
        item.description,
        item.content,
        item.image,
        item.categories
    )

    fun parse() = Item(
        id,
        guid ?: "",
        title ?: "",
        author ?: "",
        link ?: "",
        pubDate ?: "",
        description ?: "",
        content ?: "",
        image ?: "",
        categories ?: ""
    )
}
