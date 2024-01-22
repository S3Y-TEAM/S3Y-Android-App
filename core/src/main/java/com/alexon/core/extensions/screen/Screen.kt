package com.alexon.core.extensions.screen

import android.view.WindowManager
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.disableUserTouches(isDisabled: Boolean) {
    if (isDisabled) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}