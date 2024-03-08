package com.graduation.presentation.screens.auth.position

import androidx.lifecycle.MutableLiveData
import com.graduation.core.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class PositionViewModel @Inject constructor() : BaseViewModel(){

    private val _role =
        MutableLiveData<String>()
    val role = _role

    fun setRole(role: String) = saveRole(role = role)

    private fun saveRole(role: String) {
        _role.value = role
    }


}