package kosmicbor.giftapp.mymoviesapp.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentGoogleMapsBinding
import java.io.IOException

class FragmentGoogleMaps : Fragment(R.layout.fragment_google_maps) {

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentGoogleMaps()

        private const val REFRESH_PERIOD = 60000L
        private const val MINIMAL_DISTANCE = 10f
        private const val CHE_LAT = 55.1644419
        private const val CHE_LONG = 61.4368431
        private const val ZERO_VAL = 0
        private const val START_ZOOM_VALUE = 15.0f
        private const val GEOFENCE_RADIUS = 100.0f
        private const val GEOFENCE_CIRCLE_RADIUS = 100.0
        private const val EXPIRATION_DURATION = 5000L
        private const val BROADCAST_CODE = 2687
    }

    private lateinit var geofencingClient: GeofencingClient
    private lateinit var map: GoogleMap

    @RequiresApi(Build.VERSION_CODES.Q)
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> {
                    getLocation()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.location_permission_dialog_title))
                        .setMessage(getString(R.string.location_permossion_dialog_message))
                        .setPositiveButton(getString(R.string.permission_pos_btn_text)) { _, _ ->
                            requestPermission()
                        }
                        .setNegativeButton(getString(R.string.permission_dialog_neg_btn_text)) { dialog, _ ->
                            dialog.dismiss()
                        }
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.location_permission_dialog_title))
                        .setMessage(getString(R.string.location_permossion_dialog_message))
                        .setPositiveButton(getString(R.string.permission_pos_btn_text)) { _, _ ->
                            requestPermission()
                        }
                        .setNegativeButton(getString(R.string.permission_dialog_neg_btn_text)) { dialog, _ ->
                            dialog.dismiss()
                        }
                }

                else -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.use_permission_denied_alert),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private val movieTheatres = hashMapOf(
        "Almaz" to LatLng(
            55.145496915424886,
            61.45180749686623
        ),
        "Kinomax-Ural" to LatLng(
            55.157533180394466,
            61.39524925639434
        ),
        "Megapolis" to LatLng(
            55.16977772483033,
            61.39123108337636
        ),
        "Kinomax" to LatLng(
            55.17147878617332,
            61.3553493649827
        )
    )

    private lateinit var geofencePendingIntent: PendingIntent

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestPermission() {
        permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val initPlace = LatLng(CHE_LAT, CHE_LONG)

        googleMap.apply {

            addMarker(MarkerOptions().position(initPlace).title(getString(R.string.my_sity_name)))
            moveCamera(CameraUpdateFactory.newLatLngZoom(initPlace, START_ZOOM_VALUE))
            uiSettings.apply {
                isZoomControlsEnabled = true
                isCompassEnabled = true
            }

            movieTheatres.forEach {
                addMarker(MarkerOptions().position(it.value).title(it.key))
                val circleOptions = CircleOptions().apply {
                    center(it.value)
                    radius(GEOFENCE_CIRCLE_RADIUS)
                    strokeColor(Color.argb(255, 255, 0, 0))
                    fillColor(Color.argb(64, 255, 0, 0))
                    strokeWidth(4f)
                }
                addCircle(circleOptions)
            }
        }

        activateMyLocation(googleMap)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            requestPermission()
        }

        context?.let {
            geofencingClient = LocationServices.getGeofencingClient(it)
        }
    }

    @SuppressLint("MissingPermission", "UnspecifiedImmutableFlag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearch()

        val geofenceList = mutableListOf<Geofence>()
        movieTheatres.forEach {
            geofenceList.add(
                Geofence.Builder()
                    .setRequestId(it.key)
                    .setCircularRegion(it.value.latitude, it.value.longitude, GEOFENCE_RADIUS)
                    .setExpirationDuration(EXPIRATION_DURATION)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            )
        }

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            .addGeofences(geofenceList)
            .build()

        val intent = Intent(context, GeofenceBroadCastReceiver::class.java)

        geofencePendingIntent = PendingIntent.getBroadcast(
            context,
            BROADCAST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Log.d("Geofence", "It's working")
            }

            addOnFailureListener {
                Log.d("Geofence", it.localizedMessage)
            }
        }
    }

    private fun initSearch() {
        binding.addressFindBtn.setOnClickListener {

            val inputText = binding.addressInputEditText

            val address: String = inputText.text.toString().trim()

            Thread {
                try {
                    val geocoder = Geocoder(it.context)

                    val addresses = geocoder.getFromLocationName(address, 1)
                    if (addresses.size > ZERO_VAL) {
                        goToAddress(addresses, address, it)
                    }
                } catch (e: IOException) {
                    e.localizedMessage?.apply {
                        Snackbar.make(binding.root, this, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    private fun goToAddress(
        addresses: List<Address>,
        address: String,
        view: View
    ) {
        val location = LatLng(
            addresses[ZERO_VAL].latitude,
            addresses[ZERO_VAL].longitude
        )
        view.post {
            map.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(address)
            )
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    15f
                )
            )

            binding.addressField.text = address
        }
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
    }

    private val binding: FragmentGoogleMapsBinding by viewBinding(FragmentGoogleMapsBinding::bind)


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)

                    provider?.apply {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            object : LocationListener {
                                override fun onLocationChanged(location: Location) {
                                    getAddress(location)
                                }

                                override fun onProviderEnabled(provider: String) {
                                    super.onProviderEnabled(provider)
                                }

                                override fun onLocationChanged(locations: MutableList<Location>) {
                                    super.onLocationChanged(locations)
                                }

                                override fun onProviderDisabled(provider: String) {
                                    super.onProviderDisabled(provider)
                                }

                                override fun onFlushComplete(requestCode: Int) {
                                    super.onFlushComplete(requestCode)
                                }

                                override fun onStatusChanged(
                                    provider: String?,
                                    status: Int,
                                    extras: Bundle?
                                ) {
                                    super.onStatusChanged(provider, status, extras)
                                }
                            }
                        )

                    }
                } else {
                    val lastKnownLocation =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastKnownLocation?.let {
                        getAddress(lastKnownLocation)
                    }
                }
            }
        }
    }

    private fun getAddress(location: Location) {

        Thread {
            try {
                val geoCoder = Geocoder(context)

                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                activity?.runOnUiThread {
                    binding.addressField.text = addresses[0].getAddressLine(0)
                }


            } catch (e: IOException) {
                e.fillInStackTrace()
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        geofencingClient.removeGeofences(geofencePendingIntent)
    }
}


