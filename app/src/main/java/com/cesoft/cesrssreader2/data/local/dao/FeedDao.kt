package com.cesoft.cesrssreader2.data.local.dao

import androidx.room.*
import com.cesoft.cesrssreader2.data.local.entity.FeedEntity
import com.cesoft.cesrssreader2.data.local.entity.ChannelEntity

@Dao
interface FeedDao {

    //SELECT
    @Query("SELECT * FROM channels")
    suspend fun channel(): ChannelEntity?

    @Query("SELECT * FROM feeds")
    suspend fun feeds(): List<FeedEntity>

    // UPDATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChannel(channel: ChannelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFeeds(feeds: List<FeedEntity>)

    //DELETE
    @Query("DELETE FROM feeds")
    suspend fun deleteFeeds()

//    @Query("SELECT * FROM feeds")
//    fun all(): LiveData<List<Feed>>
/*
    @Query("SELECT * FROM crops ORDER BY name")
    fun allOrderByName(): LiveData<List<Crop>>

    @Query("SELECT * FROM crops WHERE id = :cropId")
    fun get(cropId: String):LiveData<Crop?>

    @Query("SELECT * FROM crops WHERE strain_id = :strainId ORDER BY name")
    fun getByStrain(strainId: Int): LiveData<List<Crop>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Crop>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Crop) : Long

    //UPDATE
    @Update
    suspend fun update(note: Crop)

    //DELETE
    @Delete
    suspend fun delete(vararg notes: Crop)*/
}
