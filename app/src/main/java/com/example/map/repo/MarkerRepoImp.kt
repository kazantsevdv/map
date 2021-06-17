package com.example.map.repo

import androidx.lifecycle.LiveData
import com.example.map.data.MarkerDB
import com.example.map.data.MarkerEntity

class MarkerRepoImp(val db: MarkerDB) : IMarkerRepo {
    override fun getMarkers(): LiveData<List<MarkerEntity>> {
        return db.MarkerDao().getAll()
    }

    override fun updateMarker(marker: MarkerEntity) {
        db.MarkerDao().update(marker)
    }

    override fun deleteMarker(marker: MarkerEntity) {
        db.MarkerDao().delete(marker)
    }

    override fun addMarker(marker: MarkerEntity) {
        db.MarkerDao().insert(marker)
    }

}