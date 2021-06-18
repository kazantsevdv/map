package com.example.map.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Marker(
    val latitude: Double,
    val longitude: Double,
    val title: String?,
    val info: String?
) : Parcelable {
    val latitudeStr: String get() = String.format("%.5f", latitude)
    val longitudeStr: String get() = String.format("%.5f", longitude)

}