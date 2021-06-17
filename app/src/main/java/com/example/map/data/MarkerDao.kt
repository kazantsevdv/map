package com.example.map.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MarkerDao {

    @Query("SELECT * FROM MarkerEntity")
    fun getAll(): LiveData<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marker: MarkerEntity)

    @Update
    fun update(marker: MarkerEntity)

    @Delete
    fun delete(marker: MarkerEntity)

}