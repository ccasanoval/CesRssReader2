package com.cesoft.cesrssreader2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cesoft.cesrssreader2.data.local.dao.RssDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import com.cesoft.cesrssreader2.data.local.entity.RssUrlEntity

@Database(
    entities = [ItemEntity::class, ChannelEntity::class, RssUrlEntity::class],
    version = 1,
    exportSchema = false)
abstract class RssDb : RoomDatabase() {
    abstract val feedDao: RssDao
    companion object {
        const val NAME = "ces-database"
    }
}