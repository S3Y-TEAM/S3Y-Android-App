package com.graduation.presentation.screens.auth.phone

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPResponse
import com.graduation.domain.usecase.auth.PhoneOTPUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(private val phoneOTPUseCase: PhoneOTPUseCase) :
    BaseViewModel() {

    private val _phoneOTPResponse =
        MutableStateFlow<ResponseState<PhoneOTPResponse>>(ResponseState.Empty())

    private val _phoneOtp =
        MutableLiveData<Int>()
    val phoneOtp = _phoneOtp

    private val _phoneError =
        MutableLiveData<String>()
    val phoneError = _phoneError

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _job: Job? = null

    fun callPhoneOTP(role: String, phoneOTPRequest: PhoneOTPRequest) {

        viewModelScope.launch {
            _job = networkCall(action = {
                phoneOTPUseCase.invoke(
                    role = role,
                    phoneOTPRequest = phoneOTPRequest
                )
            }, onReply = {
                _phoneOTPResponse.emit(it)
            }, onToken = { _token })
            validatePhoneOtpCall()
        }
    }

    private fun validatePhoneOtpCall() {
        viewModelScope.launch {
            _phoneOTPResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _phoneError.value = "Email is Empty"
                    is ResponseState.NetworkError -> _phoneError.value = "Check Internet Connection"
                    is ResponseState.Error -> _phoneError.value = it.message.toString()
                    is ResponseState.Success -> {
                        _phoneError.value = Constants.VALID
                        _phoneOtp.value = it.data!!.data.phone
                        _token.value = it.message.toString()
                        Log.d("access_token" , it.message.toString())
                    }

                    is ResponseState.UnKnownError -> _phoneError.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _phoneError.value = "NotAuthorized"
                    is ResponseState.Loading -> _phoneError.value = "loading"
                }
            }
        }
    }
}