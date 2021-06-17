package com.example.map.repo

import androidx.lifecycle.LiveData
import com.example.map.data.MarkerEntity

interface IMarkerRepo {
    fun getMarkers(): LiveData<List<MarkerEntity>>
    suspend fun updateMarker(marker: MarkerEntity)
    suspend fun deleteMarker(marker: MarkerEntity)
    suspend fun addMarker(marker: MarkerEntity)
}