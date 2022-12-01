package com.example.finalproject.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.DirecionsMapBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.*

class Directions : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val nearMopacAndWAnderson = LatLng(30.35900, -97.743083)
    private var overlayPresent = false
    private var locationPermissionGranted = false
    private var address: String? = ""
    private var origin: String? = ""
    private var originLon: Double = 0.00
    private var originLat: Double = 0.00
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.direcions_map)
        checkGooglePlayServices()

        geocoder = Geocoder(this)

        // Get restaurant address
        address = intent.getStringExtra(MainActivity.directionsKey)

        //  Check permissions and get current location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@Directions)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
            return
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFrag) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Save current location
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) {

            if (it != null && it.latitude != null && it.longitude != null) {

                // Get current location and use it to find directions to restaurant
                originLat = it.latitude
                originLon = it.longitude
                var startCoords = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                origin = startCoords.get(0)
                    .getAddressLine(0) + " " + startCoords.get(0).locality.toString()
                viewModel.netDirections(address!!, origin!!)
            } else {
                Toast.makeText(this, "Can not load directions", Toast.LENGTH_LONG).show()
                this.finish()
            }

        }


    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap


        // Get permission for map
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

        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        map.setOnMapLongClickListener {
            map.clear()
            overlayPresent = false
        }

        // If current location not yet loaded, initialize to somewhere in Austin
        if (origin == "") {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(nearMopacAndWAnderson, 15.0f))
        } else {
            var latlng = LatLng(originLat, originLon)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.0f))
        }

        // Once directions are loaded, set camera to current location and fill in route
        viewModel.getDirections().observe(this) {
            for (x in it.first().legs.first().steps) {
                map.addPolyline(
                    PolylineOptions().addAll(PolyUtil.decode(x.polyline.points)).color(Color.RED)
                )
            }
            var latlng = LatLng(originLat, originLon)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.0f))
        }

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
                    Toast.LENGTH_LONG).show() }
            }
        }
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

}
