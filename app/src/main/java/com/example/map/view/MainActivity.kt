package com.example.map.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.map.R
import com.example.map.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()
//    private val viewModelLazy: Lazy<MainActivityViewModel> = viewModel(this, MainActivityViewModel::class.java)

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {

            } else {
                Snackbar.make(binding.root, "Нет разрешения на геолокацию", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        if (!getLocationPermission()) {
            requestPermissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        setupObservers(map)
        map.setOnMapLongClickListener { latLng -> addMarker(latLng) }
    }

    private fun addMarker(latLng: LatLng) {
        viewModel.addMarker(latLng.latitude,latLng.longitude)
    }


    private fun getLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun setupObservers(geoMap: GoogleMap) {
        viewModel.getMarkers().observe(this, { it ->
            it?.let { markers ->
                markers.forEach {
                    val lat = LatLng(it.latitude, it.longitude)
                    geoMap.addMarker(
                        MarkerOptions()
                            .position(lat)
                            .title(it.title)
                    )
                }
            }
        })
    }
}
