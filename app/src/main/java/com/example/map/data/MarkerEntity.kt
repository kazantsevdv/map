package com.example.map.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(primaryKeys = ["latitude", "longitude"])
data class MarkerEntity(
    val latitude: Double,
    val longitude: Double,
    val title: String?,
    val info: String?
) : Parcelable