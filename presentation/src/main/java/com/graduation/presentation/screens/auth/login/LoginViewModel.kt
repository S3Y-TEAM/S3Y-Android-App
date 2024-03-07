package com.graduation.presentation.screens.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.BaseResponse
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.login.LoginRequest
import com.graduation.domain.models.auth.login.LoginResponse
import com.graduation.domain.models.auth.signup.User
import com.graduation.domain.usecase.auth.LoginUseCase
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {


    private val _loginResponse =
        MutableStateFlow<ResponseState<LoginResponse>>(ResponseState.Empty())
    //val sendUsernameResponse = _sendUsernameResponse.asStateFlow()

    private val _login =
        MutableLiveData<User>()
    val login = _login

    private val _loginError =
        MutableLiveData<String?>()
    val loginError = _loginError

    private var _token = MutableLiveData<String>()
    val token = _token

    private val _isUserDataSaved =
        MutableLiveData(false)
    val isUserDataSaved = _isUserDataSaved

    private val _isTokenSaved =
        MutableLiveData(false)
    val isTokenSaved = _isTokenSaved

    private val _isSuccess =
        MutableLiveData(false)
    val isSuccess = _isSuccess

    private var _job: Job? = null

    fun callLogin(role: String, loginRequest: LoginRequest) {

        viewModelScope.launch {
            _job = networkCall(action = {
                loginUseCase.invoke(
                    role = role,
                    loginRequest = loginRequest
                )
            }, onReply = {
                _loginResponse.emit(it)
            }, onToken = { _token })
            validateLoginCall()
        }
    }

    private fun validateLoginCall() {
        viewModelScope.launch {
            _loginResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _loginError.value = "Data is Empty"
                    is ResponseState.NetworkError -> _loginError.value = "Check Internet Connection"
                    is ResponseState.Error -> _loginError.value = it.message.toString()
                    is ResponseState.Success -> {
                        _login.value = it.data!!.data
                        _loginError.value = VALID
                        _token.value = it.message.toString()
                    }

                    is ResponseState.UnKnownError -> _loginError.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _loginError.value = "NotAuthorized"
                    is ResponseState.Loading -> _loginError.value = null
                }
            }
        }
    }

    fun setIsUserDataSaved(isUserDataSaved: Boolean) =
        saveIsUserDataSaved(isUserDataSaved = isUserDataSaved)

    private fun saveIsUserDataSaved(isUserDataSaved: Boolean) {
        _isUserDataSaved.value = isUserDataSaved
        successData()
    }

    fun setIsTokenSaved(isTokenSaved: Boolean) =
        saveIsTokenSaved(isTokenSaved = isTokenSaved)

    private fun saveIsTokenSaved(isTokenSaved: Boolean) {
        _isTokenSaved.value = isTokenSaved
        successData()
    }

    private fun successData() {
        if (
            _isTokenSaved.value.toString() == "true" &&
            _isUserDataSaved.value.toString() == "true"
        )
            _isSuccess.value = true
    }


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
        val isCountryCodeEntered: Boolean = false,
    ) {
        val isAllDataEntered get() = isPhoneEntered && isCountryCodeEntered
    }
}