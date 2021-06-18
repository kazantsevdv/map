package com.example.map.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.map.data.MarkerDB
import com.example.map.model.Marker
import com.example.map.util.ModelTransform

class MarkerRepoImp(private val db: MarkerDB, private val transform: ModelTransform) : IMarkerRepo {
    override fun getMarkers(): LiveData<List<Marker>> {
        return Transformations.map(db.MarkerDao().getAll()) { list ->
            list.map { transform.dbToView(it) }
        }
    }

    override suspend fun updateMarker(marker: Marker) {
        db.MarkerDao().update(transform.viewToDb(marker))
    }

    override suspend fun deleteMarker(marker: Marker) {
        db.MarkerDao().delete(transform.viewToDb(marker))
    }

    override suspend fun addMarker(marker: Marker) {
        db.MarkerDao().insert(transform.viewToDb(marker))
    }

}