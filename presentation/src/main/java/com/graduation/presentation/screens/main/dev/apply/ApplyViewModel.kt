package com.graduation.presentation.screens.main.dev.apply

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.main.dev.apply.ApplyRequest
import com.graduation.domain.models.main.dev.apply.ApplyResponse
import com.graduation.domain.models.main.dev.apply.NewApplication
import com.graduation.domain.usecase.main.dev.ApplyUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyViewModel @Inject constructor(private val applyUseCase: ApplyUseCase) :
    BaseViewModel() {


    private val _appliedResponse =
        MutableStateFlow<ResponseState<ApplyResponse>>(ResponseState.Empty())

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _appliedError = MutableLiveData<String>()
    val appliedError = _appliedError

    private val _appliedList =
        MutableLiveData<NewApplication>()
    val appliedList = _appliedList

    private var _job: Job? = null


    fun callApplyTask(
        role: String,
        taskId: Int,
        applyRequest: ApplyRequest,
    ) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    applyUseCase.invoke(
                        role = role, taskId = taskId, applyRequest = applyRequest
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
                        _appliedList.value = it.data!!.data.newApplication
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