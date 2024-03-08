package com.graduation.presentation.screens.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.username.UserNameRequest
import com.graduation.domain.models.auth.username.UsernameResponse
import com.graduation.domain.usecase.auth.UsernameUseCase
import com.graduation.presentation.Constants.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SignUpViewModel @Inject constructor(private val usernameUseCase: UsernameUseCase) :
    BaseViewModel() {

    private val _sendUsernameResponse =
        MutableStateFlow<ResponseState<UsernameResponse>>(ResponseState.Empty())
    //val sendUsernameResponse = _sendUsernameResponse.asStateFlow()

    private var _token = MutableLiveData<String>()
    val token = _token

    private val _fullName =
        MutableLiveData<String>()
    val fullName = _fullName

    private val _userName =
        MutableLiveData<String>()
    val userName = _userName

    private val _password =
        MutableLiveData<String>()
    val password = _password

    private val _confirmPassword =
        MutableLiveData<String>()
    val confirmPassword = _confirmPassword

    private val _isSuccess =
        MutableLiveData(false)
    val isSuccess = _isSuccess

    private var _job: Job? = null


    fun fullNameEntered(fullName: String) =
        validateFullName(fullName = fullName)

    fun confirmPassword(password: String, confirmPassword: String) {
        validatePassword(password = password)
        validateConfirmPassword(password = password, confirmPassword = confirmPassword)
    }

    private fun validateFullName(fullName: String) {
        if (fullName.isBlank()) {
            _fullName.value = "Full Name is Empty"
            return
        }
        if (fullName.getWordsCount() < 2) {
            _fullName.value = "Full Name is Short"
            return
        }
        if (fullName.getWordsCount() > 4) {
            _fullName.value = "Full Name is Long"
            return
        }
        if (fullName.any { it.isDigit() }) {
            _fullName.value = "invalid Full Name"
            return
        }
        _fullName.value = VALID
    }

    private fun String.getWordsCount(): Int {
        val words = this.trim()
        return words.split("\\s+".toRegex()).size
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

    fun sendUsername(role: String, userNameRequest: UserNameRequest) {
        viewModelScope.launch {
            _job =
                networkCall(action = {
                    usernameUseCase.invoke(
                        role = role,
                        userNameRequest = userNameRequest
                    )
                }, onReply = {
                    _sendUsernameResponse.emit(it)
                }, onToken = { _token })
        }
        validateUserName()
    }

    fun sendSuccess() {
        successData()
    }

    private fun successData() {
        if (
            _fullName.value == VALID &&
            _userName.value == VALID &&
            _password.value == VALID &&
            _confirmPassword.value == VALID
        )
            _isSuccess.value = true
    }

    private fun validateUserName() {
        viewModelScope.launch {
            _sendUsernameResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _userName.value = "User Name is Empty"
                    is ResponseState.NetworkError -> _userName.value = "Check Internet Connection"
                    is ResponseState.Error -> _userName.value = it.message.toString()
                    is ResponseState.Success -> {
                        _userName.value = VALID
                        _token.value = it.message.toString()
                    }
                    is ResponseState.UnKnownError -> _userName.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _userName.value = "NotAuthorized"
                    is ResponseState.Loading -> _userName.value = "loading"
                }
            }
        }
    }
}