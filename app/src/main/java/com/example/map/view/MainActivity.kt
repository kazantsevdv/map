package com.example.map.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.map.R
import com.example.map.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()
    private var map: GoogleMap? = null

    private var locationPermissionGranted = false

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                locationPermissionGranted = true
                updateLocationUI()
            } else {
                locationPermissionGranted = false
                Snackbar.make(binding.root, getString(R.string.no_geo_perm), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (getLocationPermission()) {
            locationPermissionGranted = true
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_hist) {
            val intent = Intent(this, HistActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onMapReady(map: GoogleMap) {
        this.map = map
        setupObservers(map)
        map.setOnMapLongClickListener { latLng -> addMarker(latLng) }
        updateLocationUI()
    }

    private fun addMarker(latLng: LatLng) {
        viewModel.addMarker(latLng.latitude, latLng.longitude)
    }

    private fun getLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this.applicationContext,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun setupObservers(geoMap: GoogleMap) {
        viewModel.getMarkers().observe(this, { it ->
            it?.let { markers ->
                geoMap.clear()
                markers.forEach {
                    val lat = LatLng(it.latitude, it.longitude)
                    geoMap.addMarker(
                        MarkerOptions()
                            .position(lat)
                            .snippet(it.info)
                            .title(it.title)
                    )
                }
            }
        })
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                requestPermissions.launch(ACCESS_FINE_LOCATION)

            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    companion object {
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }
}
