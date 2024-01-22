package com.alexon.presentation.screens.splash

import androidx.lifecycle.lifecycleScope
import com.alexon.core.base.ui.BaseActivity
import com.alexon.core.extensions.navigation.startActivity
import com.alexon.core.extensions.navigation.startActivityWithExtras
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_KEY
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_AUTHENTICATE
import com.alexon.presentation.Constants.NAV_ARGS_AUTH_ON_BOARDING
import com.alexon.core.extensions.screen.changeStatusBarColor
import com.alexon.core.utils.language.getCurrentAppLanguage
import com.alexon.core.utils.permission.PermissionType
import com.alexon.core.utils.permission.requestPermissions
import com.alexon.presentation.R
import com.alexon.presentation.databinding.ActivitySplashBinding
import com.alexon.presentation.screens.auth.AuthActivity
import com.alexon.presentation.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun initialize() {
        //statusBar
        changeStatusBarColor(R.color.white, isContentLight = true, isTransparent = true)

        //set language if empty based on device language
        sharedPreferenceHelper.appLanguage = getCurrentAppLanguage(sharedPreferenceHelper)

        //request notification permission
        requestPermissions(permissionType = PermissionType.Notification,
            sharedPreferenceHelper = sharedPreferenceHelper,
            showRationaleDialog = false,
            showSettingDialog = false,
            onAny = {
                //navigating
                lifecycleScope.launch {
                    delay(1600)
                    handleNavigation()
                }
            })
    }

    private fun handleNavigation() {
        //home
        if (isUserOpenedAppBefore() && isUserLoggedIn()) {
            startActivity(activity = MainActivity(), finish = true)
            return
        }

        //auth
        if (isUserOpenedAppBefore() && !isUserLoggedIn()) {
            startActivityWithExtras(
                activity = AuthActivity(),
                extras = mapOf(NAV_ARGS_AUTH_KEY to NAV_ARGS_AUTH_AUTHENTICATE),
                finish = true
            )
            return
        }

        //onboarding
        startActivityWithExtras(
            activity = AuthActivity(),
            extras = mapOf(NAV_ARGS_AUTH_KEY to NAV_ARGS_AUTH_ON_BOARDING),
            finish = true
        )
    }
}