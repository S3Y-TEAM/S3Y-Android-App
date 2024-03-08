package com.graduation.presentation.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.graduation.presentation.R
import com.graduation.presentation.databinding.ActivityMainBinding
import com.graduation.presentation.screens.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var encryptedSharedPreference: EncryptedSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.mainAppBar.appBarTitle.text = resources.getText(R.string.welcome_to_s3y)
        binding.mainAppBar.appBarBackArrow.visibility = View.GONE
        val fName = encryptedSharedPreference.userData.fName
        val lName = encryptedSharedPreference.userData.lName
        binding.fullName.text = "$fName $lName"

        binding.logoutButton.setOnClickListener {
            binding.logoutButton.start()
            encryptedSharedPreference.loggedIn = ""
            binding.logoutButton.complete(true)
            successLogin()
        }
    }
    private fun successLogin() {
        startActivity(Intent(this, AuthActivity::class.java))
        this.finish()
    }
}