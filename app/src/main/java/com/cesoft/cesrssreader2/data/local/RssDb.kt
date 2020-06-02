package com.cesoft.cesrssreader2.data.local

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cesoft.cesrssreader2.data.local.dao.RssDao
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import com.cesoft.cesrssreader2.data.local.entity.RssUrlEntity

@Database(
    entities = [ItemEntity::class, ChannelEntity::class, RssUrlEntity::class],
    version = RssDbMigrations.latestVersion,
    exportSchema = false)
abstract class RssDb : RoomDatabase() {
    abstract val feedDao: RssDao
    companion object {
        private const val NAME = "ces-database"

        fun buildDefault(context: Context) =
            Room.databaseBuilder(context, RssDb::class.java, NAME)
                .fallbackToDestructiveMigration()
                .addMigrations(*RssDbMigrations.allMigrations)
                .build()

        @VisibleForTesting
        fun buildTest(context: Context) =
            Room.inMemoryDatabaseBuilder(context, RssDb::class.java)
                .build()
    }
}