package com.graduation.presentation.screens.auth.forgetpassword

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.forgetpassword.ForgetPasswordRequest
import com.graduation.domain.usecase.auth.ForgetPasswordUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel  @Inject constructor(private val forgetPasswordUseCase: ForgetPasswordUseCase) :
    BaseViewModel() {


    private val _emailOTPResponse =
        MutableStateFlow<ResponseState<EmailOTPResponse>>(ResponseState.Empty())

    private val _emailOtp =
        MutableLiveData<Int>()
    val emailOtp = _emailOtp

    private val _emailError =
        MutableLiveData<String>()
    val emailError = _emailError

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _job: Job? = null

    fun callEmailOTP(role: String, forgetPasswordRequest: ForgetPasswordRequest) {

        viewModelScope.launch {
            _job = networkCall(action = {
                forgetPasswordUseCase.invoke(
                    role = role,
                    forgetPasswordRequest = forgetPasswordRequest
                )
            }, onReply = {
                _emailOTPResponse.emit(it)
            }, onToken = { _token })
            validateEmailOtpCall()
        }
    }

    private fun validateEmailOtpCall() {
        viewModelScope.launch {
            _emailOTPResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _emailError.value = "Email is Empty"
                    is ResponseState.NetworkError -> _emailError.value = "Check Internet Connection"
                    is ResponseState.Error -> _emailError.value = it.message.toString()
                    is ResponseState.Success -> {
                        Log.d("LOGDATA" , it.toString())
                        _emailError.value = Constants.VALID
                        _emailOtp.value = it.data!!.data.codeNumber
                        _token.value = it.message.toString()
                    }

                    is ResponseState.UnKnownError -> _emailError.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _emailError.value = "NotAuthorized"
                    is ResponseState.Loading -> _emailError.value = "loading"
                }
            }
        }
    }



}