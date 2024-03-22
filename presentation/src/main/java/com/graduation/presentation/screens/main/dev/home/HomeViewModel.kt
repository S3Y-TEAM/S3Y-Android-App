package com.graduation.presentation.screens.main.dev.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.main.user.home.HomeResponse
import com.graduation.domain.models.main.user.home.Task
import com.graduation.domain.usecase.main.dev.HomeUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : BaseViewModel() {

    private val _appliedResponse =
        MutableStateFlow<ResponseState<HomeResponse>>(ResponseState.Empty())

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _appliedError = MutableLiveData<String>()
    val appliedError = _appliedError

    private val _appliedList =
        MutableLiveData<List<Task>>()
    val appliedList = _appliedList

    private var _job: Job? = null


    fun callHome(
        role: String,
        category: String,
    ) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    homeUseCase.invoke(
                        role = role,
                        category = category
                    )
                }, onReply = {
                    _appliedResponse.emit(it)
                }, onToken = { _token })


        }
        validateAppliedCall()
    }

    private fun validateAppliedCall() {
        viewModelScope.launch {
            _appliedResponse.collect {
                when (it) {
                    is ResponseState.Empty -> {
                        _appliedError.value = "Categories is Empty"
                        Log.d("suzan", "Categories is Empty")
                    }

                    is ResponseState.NetworkError -> {
                        _appliedError.value =
                            "Check Internet Connection"
                        Log.d("suzan", "Check Internet Connection")

                    }

                    is ResponseState.Error -> {
                        _appliedError.value = it.message.toString()
                        Log.d("suzan", it.message.toString())

                    }

                    is ResponseState.Success -> {
                        _appliedError.value = Constants.VALID
                        _appliedList.value = it.data!!.data.tasks
                        Log.d("suzan", "done")
                    }

                    is ResponseState.UnKnownError -> {
                        _appliedError.value = "UnKnownError"
                        Log.d("suzan", "UnKnownError")

                    }

                    is ResponseState.NotAuthorized -> {
                        _appliedError.value = "NotAuthorized"
                        Log.d("suzan", "NotAuthorized")

                    }

                    is ResponseState.Loading -> {
                        _appliedError.value = "loading"
                        Log.d("suzan", "loading")

                    }
                }
            }
        }
    }


}