package com.example.map.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.map.model.Marker
import com.example.map.repo.IMarkerRepo
import kotlinx.coroutines.launch

class HistActivityViewModel(private val repo: IMarkerRepo) : ViewModel() {
    fun getMarkers() = repo.getMarkers()


    fun removeMarker(data: Marker) {
        viewModelScope.launch {
            repo.deleteMarker(
                (data)
            )
        }
    }

    fun updateMarker(data: Marker) {
        viewModelScope.launch {
            repo.updateMarker(data)
        }
    }

}