package com.example.map.repo

import androidx.lifecycle.LiveData
import com.example.map.data.MarkerEntity

interface IMarkerRepo {
    fun getMarkers(): LiveData<List<MarkerEntity>>
    fun updateMarker(marker:MarkerEntity)
    fun deleteMarker(marker:MarkerEntity)
    fun addMarker(marker:MarkerEntity)
}