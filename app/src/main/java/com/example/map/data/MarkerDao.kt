package com.example.map.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MarkerDao {

    @Query("SELECT * FROM MarkerEntity")
    fun getAll(): LiveData<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun  insert(marker: MarkerEntity)

    @Update
    suspend fun update(marker: MarkerEntity)

    @Delete
    suspend  fun delete(marker: MarkerEntity)

}