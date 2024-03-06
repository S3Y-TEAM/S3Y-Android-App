package com.graduation.presentation.screens.auth.complete

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.signup.SignUpRequest
import com.graduation.domain.models.auth.signup.SignUpResponse
import com.graduation.domain.models.auth.signup.SignUpResponseItem
import com.graduation.domain.usecase.auth.SignUpUseCase
import com.graduation.presentation.Constants.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) :
    BaseViewModel() {

    private val _signUpResponse =
        MutableStateFlow<ResponseState<SignUpResponse>>(ResponseState.Empty())
    val signUpResponse = _signUpResponse.asStateFlow()

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _signUpError = MutableLiveData<String>()
    val signUpError = _signUpError

    private val _categoriesList =
        MutableLiveData<SignUpResponseItem>()
    val categoriesList = _categoriesList

    private var _job: Job? = null

    fun callSignup(role: String, signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            _job =
                networkCall(action = {
                    signUpUseCase.invoke(
                        role = role,
                        signUpRequest = signUpRequest
                    )
                }, onReply = {
                    _signUpResponse.emit(it)
                }, onToken = { _token })


        }
        validateSignupCall()
    }

    private fun validateSignupCall() {
        viewModelScope.launch {
            _signUpResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _signUpError.value = "Empty"
                    is ResponseState.NetworkError -> _signUpError.value =
                        "Check Internet Connection"

                    is ResponseState.Error -> _signUpError.value = it.message.toString()
                    is ResponseState.Success -> {
                        _signUpError.value = VALID
                        _categoriesList.value = it.data!!.data
                        Log.d("allData" , _categoriesList.value.toString())
                    }

                    is ResponseState.UnKnownError -> _signUpError.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _signUpError.value = "NotAuthorized"
                    is ResponseState.Loading -> _signUpError.value = "loading"
                }
            }
        }
    }


}