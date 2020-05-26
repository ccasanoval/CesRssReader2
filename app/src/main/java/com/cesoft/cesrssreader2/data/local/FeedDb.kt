package com.cesoft.cesrssreader2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cesoft.cesrssreader2.data.local.dao.FeedDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.FeedEntity


@Database(entities = [FeedEntity::class, ChannelEntity::class], version = 1, exportSchema = false)
abstract class FeedDb : RoomDatabase() {
    abstract val feedDao: FeedDao

    companion object {
        const val NAME = "ces-database"
    }
}