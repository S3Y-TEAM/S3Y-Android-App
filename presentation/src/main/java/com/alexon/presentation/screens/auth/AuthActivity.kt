package com.alexon.presentation.screens.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexon.core.extensions.navigation.setStartDestination
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_KEY
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_AUTHENTICATE
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_ON_BOARDING
import com.alexon.presentation.R
import com.alexon.presentation.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent?.getStringExtra(NAV_ARGS_AUTH_KEY)) {
//            NAV_ARGS_AUTH_ON_BOARDING -> handleSetStartDestination(R.id.onBoardingFragment)
            NAV_ARGS_AUTH_AUTHENTICATE -> handleSetStartDestination(R.id.loginFragment)
            else -> handleSetStartDestination(R.id.loginFragment)
        }
    }

    private fun handleSetStartDestination(fragmentId: Int) {
        setStartDestination(R.id.auth_nav_host, R.navigation.auth_graph, fragmentId)
    }

}