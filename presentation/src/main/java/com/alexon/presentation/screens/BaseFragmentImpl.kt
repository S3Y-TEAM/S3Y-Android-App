package com.alexon.presentation.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.alexon.core.base.ui.BaseFragment
import com.alexon.core.extensions.navigation.startActivityAndClearStack
import com.alexon.presentation.screens.auth.AuthActivity

abstract class BaseFragmentImpl<B : ViewBinding>(
    inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BaseFragment<B>(inflateMethod) {

    override fun logout() {
        encryptedSharedPreference.clean()
        //navigate
        startActivityAndClearStack(AuthActivity::class.java)
    }
}