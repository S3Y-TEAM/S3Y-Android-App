package com.alexon.presentation.screens.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexon.core.constants.Constants.NAV_ARGS_AUTH_KEY
import com.alexon.core.constants.Constants.NAV_ARGS_AUTH_LOGIN
import com.alexon.core.constants.Constants.NAV_ARGS_AUTH_ON_BOARDING
import com.alexon.core.extensions.screen.changeStatusBarColor
import com.alexon.core.utils.language.getCurrentAppLanguage
import com.alexon.core.utils.permission.PermissionType
import com.alexon.core.utils.permission.requestPermissions
import com.alexon.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.alexon.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.alexon.presentation.R
import com.alexon.presentation.databinding.ActivitySplashBinding
import com.alexon.presentation.screens.auth.AuthActivity
import com.alexon.presentation.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var encryptedSharedPreference: EncryptedSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor(R.color.white, isContentLight = true, isTransparent = true)

        //set language if empty based on device language
        sharedPreferenceHelper.appLanguage = getCurrentAppLanguage(sharedPreferenceHelper)

        //request notification permission
        requestPermissions(
            permissionType = PermissionType.Notification,
            sharedPreferenceHelper = sharedPreferenceHelper,
            onAny = {
                //animation
                lifecycleScope.launch {
                    animateLogo()
                    animateSlogan()
                }

                //navigating
                lifecycleScope.launch {
                    delay(1600)
//                    handleNavigation()
                }
            }
        )

    }

    private fun handleNavigation() {
        if (isUserOpenedAppBefore()) {
            if (isUserLoggedIn()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(
                    Intent(this@SplashActivity, AuthActivity::class.java).putExtra(
                        NAV_ARGS_AUTH_KEY, NAV_ARGS_AUTH_LOGIN
                    )
                )
            }
        } else {
            startActivity(
                Intent(this@SplashActivity, AuthActivity::class.java).putExtra(
                    NAV_ARGS_AUTH_KEY, NAV_ARGS_AUTH_ON_BOARDING
                )
            )
        }
        finish()
    }

    /** Animation **/
    private fun animateLogo() {
        lifecycleScope.launch {
            animateLetter(binding.letter1, 100f, 300)
            animateLetter(binding.letter2, 100f, 400)
            animateLetter(binding.letter3, 100f, 500)
            animateLetter(binding.letter4, 100f, 600)
            animateLetter(binding.letter5, 100f, 700)
            animateLetter(binding.letter6, 100f, 800)
        }
    }

    private fun animateSlogan() {
        lifecycleScope.launch {
            binding.sloganIv.alpha = 0f
            binding.sloganIv.translationX = -100f
            delay(800)
            binding.sloganIv.animate().translationX(0f).duration = 400
            binding.sloganIv.animate().alpha(1f).duration = 500
        }
    }

    private fun animateLetter(view: View, x: Float, duration: Long) {
        lifecycleScope.launch {
            view.translationX = x
            view.alpha = 0f
            view.animate().translationX(0f).duration = duration
            view.animate().alpha(1f).duration = duration
        }
    }

//    private fun getCurrentAppLanguage(): String {
//        return if (sharedPreferenceHelper.appLanguage == "") {
//            getDeviceLocaleLanguage()
//        } else {
//            sharedPreferenceHelper.appLanguage
//        }
//    }

    private fun isUserLoggedIn(): Boolean {
        return encryptedSharedPreference.token != ""
    }

    private fun isUserOpenedAppBefore() = sharedPreferenceHelper.openedTheAppBefore

}