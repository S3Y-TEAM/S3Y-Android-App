package com.graduation.presentation.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.BaseResponse
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    /** login response **/
    private val _sendOtpResponse =
        MutableStateFlow<ResponseState<BaseResponse>>(ResponseState.Empty())
    val sendOtpResponse = _sendOtpResponse.asStateFlow()

    val currentCountryId = MutableStateFlow(0)
//    fun sendOtp(otpRequest: SendOtpRequest) {
//        networkCall(action = { authRepo.sendOtp(otpRequest) },
//            onReply = { _sendOtpResponse.emit(it) })
//    }

    /** button state **/
    private val _loginButtonState = MutableStateFlow(LoginButtonState())
    val loginButtonState = _loginButtonState.asStateFlow()

    val phoneMaxLength = MutableStateFlow(0)

    fun onPhoneChange(length: Int, maxLength: Int) {
        viewModelScope.launch {
            _loginButtonState.emit(
                _loginButtonState.value.copy(isPhoneEntered = length == maxLength)
            )
        }
    }

    fun onCountryCodeChanged(countryCode: String) {
        viewModelScope.launch {
            _loginButtonState.emit(
                _loginButtonState.value.copy(isCountryCodeEntered = countryCode != "-1" && countryCode.isNotEmpty())
            )
        }
    }

    data class LoginButtonState(
        val isPhoneEntered: Boolean = false,
        val isCountryCodeEntered: Boolean = false
    ) {
        val isAllDataEntered get() = isPhoneEntered && isCountryCodeEntered
    }
}