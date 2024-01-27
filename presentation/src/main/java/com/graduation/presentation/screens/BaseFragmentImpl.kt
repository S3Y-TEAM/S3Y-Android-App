package com.graduation.presentation.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.graduation.core.base.ui.BaseFragment
import com.graduation.core.extensions.navigation.startActivityAndClearStack
import com.graduation.presentation.screens.auth.AuthActivity

abstract class BaseFragmentImpl<B : ViewBinding>(
    inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BaseFragment<B>(inflateMethod) {

    override fun logout() {
        encryptedSharedPreference.clean()
        //navigate
        startActivityAndClearStack(AuthActivity::class.java)
    }
}