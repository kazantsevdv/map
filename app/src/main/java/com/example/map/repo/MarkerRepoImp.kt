package com.example.map.repo

import androidx.lifecycle.LiveData
import com.example.map.data.MarkerDB
import com.example.map.data.MarkerEntity

class MarkerRepoImp(val db: MarkerDB) : IMarkerRepo {
    override fun getMarkers(): LiveData<List<MarkerEntity>> {
        return db.MarkerDao().getAll()
    }

    override suspend fun updateMarker(marker: MarkerEntity) {
        db.MarkerDao().update(marker)
    }

    override suspend fun deleteMarker(marker: MarkerEntity) {
        db.MarkerDao().delete(marker)
    }

    override suspend fun addMarker(marker: MarkerEntity) {
        db.MarkerDao().insert(marker)
    }

}