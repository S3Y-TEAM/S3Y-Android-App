package com.graduation.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.graduation.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottom bar
//        BottomBarUtils(this, getNavController(R.id.main_nav_host), binding,
//            sharedPreferenceHelper
//        ).setupBottomBar()
    }
}