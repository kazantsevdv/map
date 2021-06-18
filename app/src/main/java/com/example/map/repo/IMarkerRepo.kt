package com.example.map.repo

import androidx.lifecycle.LiveData
import com.example.map.model.Marker

interface IMarkerRepo {
    fun getMarkers(): LiveData<List<Marker>>
    suspend fun updateMarker(marker: Marker)
    suspend fun deleteMarker(marker: Marker)
    suspend fun addMarker(marker: Marker)
}