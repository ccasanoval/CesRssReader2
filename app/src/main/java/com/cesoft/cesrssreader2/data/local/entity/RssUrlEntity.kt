package com.cesoft.cesrssreader2.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "rss", indices = [androidx.room.Index(value = ["url"])])
data class RssUrlEntity(
    @PrimaryKey val url: String
)