package com.example.map.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.map.model.Marker
import com.example.map.repo.IMarkerRepo
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repo: IMarkerRepo) : ViewModel() {
    fun getMarkers() = repo.getMarkers()

    fun addMarker(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repo.addMarker(Marker(latitude, longitude, "", ""))
        }
    }

}