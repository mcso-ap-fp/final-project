package com.example.finalproject.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.api.RestaurantData
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding
import com.example.finalproject.databinding.DirecionsMapBinding
import com.example.finalproject.databinding.RestaurantDetailsBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.*
import okhttp3.internal.notify

class Directions : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder
    private lateinit var placesClient: PlacesClient
    private lateinit var overlayBitmap1: Deferred<Bitmap>
    private lateinit var overlay1: GroundOverlay
    private lateinit var overlayBitmap2: Deferred<Bitmap>
    private lateinit var overlay2: GroundOverlay
    private val nearMopacAndWAnderson = LatLng(30.35900, -97.743083)
    private var overlayPresent = false
    private var locationPermissionGranted = false
    private var address: String? = ""
    private val viewModel: MainViewModel by viewModels()
    private var polylines = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(activityMainBinding.root)

        //val directionsBinding = DirecionsMapBinding.inflate(layoutInflater)
        setContentView(R.layout.direcions_map)

        address = intent.getStringExtra(MainActivity.directionsKey)
        viewModel.netDirections(address!!)
        viewModel.getDirections().observe(this) {
            for (x in it.first().legs.first().steps){
                polylines.add(x.polyline.points)
                //map.addPolyline(PolylineOptions().addAll(PolyUtil.decode(x.polyline.points)).color(Color.RED))
            }
        }

        //Thread.sleep(5_000)

        checkGooglePlayServices()
        requestPermission()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFrag) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geocoder = Geocoder(this)

        // Initialize Places.
        //Places.initialize(applicationContext, resources.getString(R.string.google_maps_key))
        // Create a new Places client instance.
        //placesClient = Places.createClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        var currentAddress = geocoder.getFromLocationName(address, 5)

/*        for (x in viewModel.currentDirections.value!!.first().legs.first().steps) {
            polylines.add(x.polyline.points)
        }*/
        map = googleMap

        // Get permission from user for location and show self-dot
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (!locationPermissionGranted) {
                Toast.makeText(this,
                    "Unable to show location - permission required",
                    Toast.LENGTH_LONG).show()
                return
            }
        }
        //SSS
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        //EEE // XXX enable location and enable UI for my location

        // Hybrid gives terrain but also poi
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        map.setOnMapLongClickListener {
            // Goodbye markers
            map.clear()
            overlayPresent = false
        }

        // Go to initial location
       if (currentAddress != null) {
            var location = currentAddress!!.get(0)
            var latlng = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.0f))

        } else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(nearMopacAndWAnderson, 15.0f))
        }

        viewModel.netDirections(address!!)
        viewModel.getDirections().observe(this) {
            for (x in it.first().legs.first().steps){
                //polylines.add(x.polyline.points)
                map.addPolyline(PolylineOptions().addAll(PolyUtil.decode(x.polyline.points)).color(Color.RED))
            }
        }



        //for (x in polylines) {
        //    map.addPolyline(PolylineOptions().addAll(PolyUtil.decode(x)).color(Color.RED))
        //}
    }


    private fun checkGooglePlayServices() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode =
            googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, 257)?.show()
            } else {
                Log.i(javaClass.simpleName,
                    "This device must install Google Play Services.")
                finish()
            }
        }
    }

    private fun requestPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    locationPermissionGranted = true
                } else -> {
                Toast.makeText(this,
                    "Unable to show location - permission required",
                    Toast.LENGTH_LONG).show()
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }

}