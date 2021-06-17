package com.example.map.view

import androidx.lifecycle.ViewModel
import com.example.map.data.MarkerEntity
import com.example.map.repo.IMarkerRepo

class MainActivityViewModel(private val repo: IMarkerRepo) : ViewModel() {
    fun getMarkers() = repo.getMarkers()
    fun addMarker(
        latitude: Double,
        longitude: Double
    ) {
        repo.addMarker(MarkerEntity(latitude, longitude, "", ""))
    }


}