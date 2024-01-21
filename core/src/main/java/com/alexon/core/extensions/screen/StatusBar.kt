package com.alexon.core.extensions.screen

import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.changeStatusBarColor(
    color: Int,
    isContentLight: Boolean,
    isTransparent: Boolean = false
) {
    //color
    window.statusBarColor = ContextCompat.getColor(this, color)

    //content
    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = !isContentLight

    WindowCompat.setDecorFitsSystemWindows(window, true)

    if (isTransparent) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}