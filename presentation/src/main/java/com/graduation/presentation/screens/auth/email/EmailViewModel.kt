package com.graduation.presentation.screens.auth.email

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.Auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.Auth.email.EmailOTPResponse
import com.graduation.domain.usecase.auth.EmailOTPUseCase
import com.graduation.presentation.Constants.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(private val emailOTPUseCase: EmailOTPUseCase) :
    BaseViewModel() {


    private val _emailOTPResponse =
        MutableStateFlow<ResponseState<EmailOTPResponse>>(ResponseState.Empty())
    //val sendUsernameResponse = _sendUsernameResponse.asStateFlow()

    private val _emailOtp =
        MutableLiveData<Int>()
    val emailOtp = _emailOtp

    private val _emailError =
        MutableLiveData<String>()
    val emailError = _emailError

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _job: Job? = null

    fun callEmailOTP(role: String, emailOTPRequest: EmailOTPRequest) {

        viewModelScope.launch {
            _job = networkCall(action = {
                emailOTPUseCase.invoke(
                    role = role,
                    emailOTPRequest = emailOTPRequest
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
                        _emailError.value = VALID
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