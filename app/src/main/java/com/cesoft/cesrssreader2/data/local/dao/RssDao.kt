package com.cesoft.cesrssreader2.data.local.dao

import androidx.room.*
import com.cesoft.cesrssreader2.data.local.entity.ItemEntity
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity
import com.cesoft.cesrssreader2.data.local.entity.RssUrlEntity

//TODO: Save all feeds or just the last?
@Dao
interface RssDao {

    //SELECT
    @Query("SELECT * FROM rss")
    suspend fun rssUrls(): List<RssUrlEntity>

    @Query("SELECT * FROM channels")
    suspend fun channel(): ChannelEntity?

    @Query("SELECT * FROM items")
    suspend fun items(): List<ItemEntity>

    // UPDATE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRssUrl(rssUrl: RssUrlEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChannel(channel: ChannelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItems(items: List<ItemEntity>)

    //DELETE
    @Query("DELETE FROM rss")
    suspend fun deleteRssUrl()

    @Query("DELETE FROM items")
    suspend fun deleteItems()
}
