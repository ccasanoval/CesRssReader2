package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//TODO: Order by last used...
@Keep
@Entity(tableName = "rss", indices = [Index(value = ["url", "created"])])
data class RssUrlEntity(
    @PrimaryKey val url: String,
    val created: Long = System.currentTimeMillis()
)