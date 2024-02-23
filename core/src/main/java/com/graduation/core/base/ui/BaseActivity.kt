package com.graduation.core.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.graduation.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(
    private val inflateMethod: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private lateinit var _binding: VB
    protected val binding: VB get() = _binding

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var encryptedSharedPreference: EncryptedSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateMethod.invoke(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    abstract fun initialize()

    protected fun isUserLoggedIn() = encryptedSharedPreference.loggedIn != ""

    protected fun isUserOpenedAppBefore() = sharedPreferenceHelper.openedTheAppBefore
}