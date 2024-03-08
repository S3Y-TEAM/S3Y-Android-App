package com.graduation.presentation.screens.auth.resetpassword

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.resetpassword.ResetPasswordRequest
import com.graduation.domain.models.auth.resetpassword.ResetPasswordResponse
import com.graduation.domain.usecase.auth.ResetPasswordUseCase
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPasswordUseCase: ResetPasswordUseCase) :
    BaseViewModel() {

    private val _resetPasswordResponse =
        MutableStateFlow<ResponseState<ResetPasswordResponse>>(ResponseState.Empty())

    private val _password =
        MutableLiveData<String>()
    val password = _password

    private val _confirmPassword =
        MutableLiveData<String>()
    val confirmPassword = _confirmPassword

    private val _isSuccess =
        MutableLiveData(false)
    val isSuccess = _isSuccess

    private var _token = MutableLiveData<String>()
    //val token = _token

    private val _errorPassword =
        MutableLiveData<String>()
    val errorPassword = _errorPassword

    private var _job: Job? = null

    fun savePassword(role: String, resetPasswordRequest: ResetPasswordRequest) {
        viewModelScope.launch {
            _job =
                networkCall(action = {
                    resetPasswordUseCase.invoke(
                        role = role,
                        resetPasswordRequest = resetPasswordRequest
                    )
                }, onReply = {
                    _resetPasswordResponse.emit(it)
                }, onToken = { _token })
            validateResetPasswordCall()
        }
    }

    fun confirmPassword(password: String, confirmPassword: String) {
        validatePassword(password = password)
        validateConfirmPassword(password = password, confirmPassword = confirmPassword)
    }

    fun sendSuccess() {
        successData()
    }

    private fun successData() {
        if (
            _password.value == Constants.VALID &&
            _confirmPassword.value == Constants.VALID
        )
            _isSuccess.value = true
    }


    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _password.value = "Password is Empty"
            return
        }
        if (password.length < 8) {
            _password.value = "Password is too Short"
            return
        }
        if (password.length > 20) {
            _password.value = "Password is too Long"
            return
        }
        val passwordIsValid = password.any { it.isUpperCase() }
        if (!passwordIsValid) {
            _password.value = "Password Must Contain Upper Case"
            return
        }
        _password.value = VALID

    }

    private fun validateConfirmPassword(password: String, confirmPassword: String) {
        if (confirmPassword.isBlank()) {
            _confirmPassword.value = "Confirm Password is Empty"
            return
        }
        if (password != confirmPassword) {
            _confirmPassword.value = "Confirm Password not Match Password"
            return
        }
        _confirmPassword.value = VALID
    }


    private fun validateResetPasswordCall() {
        viewModelScope.launch {
            _resetPasswordResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _errorPassword.value = "Email is Empty"
                    is ResponseState.NetworkError -> _errorPassword.value =
                        "Check Internet Connection"

                    is ResponseState.Error -> _errorPassword.value = it.message.toString()
                    is ResponseState.Success -> {
                        _errorPassword.value = VALID
                        Log.d("VALID" , _errorPassword.value.toString())
                    }

                    is ResponseState.UnKnownError -> _errorPassword.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _errorPassword.value = "NotAuthorized"
                    is ResponseState.Loading -> _errorPassword.value = "loading"
                }
            }
        }
    }


}