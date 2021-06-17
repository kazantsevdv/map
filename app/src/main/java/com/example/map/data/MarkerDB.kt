package com.example.map.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [MarkerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MarkerDB: RoomDatabase(){
    abstract fun MarkerDao(): MarkerDao

}