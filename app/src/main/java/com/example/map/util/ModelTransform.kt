package com.example.map.util

import com.example.map.data.MarkerEntity
import com.example.map.model.Marker


class ModelTransform {
    fun dbToView(data: MarkerEntity) = Marker(
        data.latitude,
        data.longitude,
        data.title,
        data.info
    )

    fun viewToDb(data: Marker) = MarkerEntity(
        data.latitude,
        data.longitude,
        data.title,
        data.info
    )
}