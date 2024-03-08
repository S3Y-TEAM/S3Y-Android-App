package com.graduation.presentation.screens.auth.position

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.toastMe
import com.graduation.presentation.Constants.DEVELOPER_KEY
import com.graduation.presentation.Constants.SKILLED_KEY
import com.graduation.presentation.Constants.USER_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentPositionBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class PositionFragment :
    BaseFragmentImpl<FragmentPositionBinding>(FragmentPositionBinding::inflate) {

    override val viewModel: PositionViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        setLocationData()
        checkLocationPermission()

    }

    private fun setLocationData() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geocoder = Geocoder(requireContext(), Locale.getDefault())
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                toastMe(requireContext(), "Please allow us to access location")
            }
        }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    getAddressFromLocation(latitude, longitude)
                } else {
                    toastMe(requireContext(), "Please Open Your Location")
                }
            }
            .addOnFailureListener { e ->
                Log.d("TAG", e.toString())
            }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address = addresses[0]
                val addressLine = address.getAddressLine(0)
                sharedViewModel.setAddress(address = addressLine)
                val city = address.locality
                sharedViewModel.setCity(city = city)
                val country = address.countryName
                sharedViewModel.setCountry(country = country)
            } else {
                Log.d("latitude", "No Address")
            }
        } catch (e: IOException) {
            Log.d("TAG", e.toString())
        }
    }

    override fun setOnClickListener() {
        binding.apply {
            developerButton.apply {
                setOnClickListener {
                    lifecycleScope.launch {
                        loadingDrawable.strokeWidth = textSize * 0.14f
                        start()
                        delay(500)
                        complete(true)
                        delay(100)
                        buttonAction(DEVELOPER_KEY)
                    }
                }
            }

            skilledButton.apply {
                setOnClickListener {
                    lifecycleScope.launch {
                        loadingDrawable.strokeWidth = textSize * 0.14f
                        start()
                        delay(500)
                        complete(true)
                        delay(100)
                        buttonAction(SKILLED_KEY)
                    }
                }
            }
            userButton.apply {
                setOnClickListener {
                    lifecycleScope.launch {
                        loadingDrawable.strokeWidth = textSize * 0.14f
                        start()
                        delay(300)
                        complete(true)
                        delay(100)
                        buttonAction(USER_KEY)
                    }
                }
            }
            login.setOnClickListener {
                navigateTo(R.id.action_positionFragment_to_loginFragment)
            }
            onBackPress {
                requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
                    return@addCallback requireActivity().finish()
                }
            }

        }
    }

    private fun buttonAction(position: String) {
        sharedViewModel.setRole(position)
        navigateTo(
            R.id.action_positionFragment_to_signUpFragment
        )
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.background, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

}