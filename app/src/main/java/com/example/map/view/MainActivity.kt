package com.example.map.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
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

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                Snackbar.make(binding.root, "ok", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                Snackbar.make(binding.root, getString(R.string.no_geo_perm), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        requestPermissions.launch(ACCESS_FINE_LOCATION)
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
        setupObservers(map)
        map.setOnMapLongClickListener { latLng -> addMarker(latLng) }
    }

    private fun addMarker(latLng: LatLng) {
        viewModel.addMarker(latLng.latitude, latLng.longitude)
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
    companion object{
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }
}
