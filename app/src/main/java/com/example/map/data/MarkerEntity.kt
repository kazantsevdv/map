package com.example.map.data

import androidx.room.Entity


@Entity(primaryKeys = ["latitude", "longitude"])
data class MarkerEntity(
    val latitude: Double,
    val longitude: Double,
    val title: String?,
    val info: String?
) 