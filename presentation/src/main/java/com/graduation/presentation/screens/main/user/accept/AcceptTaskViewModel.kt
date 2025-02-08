package com.graduation.presentation.screens.main.user.accept

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.main.user.accept.AcceptResponse
import com.graduation.domain.models.main.user.accept.Task
import com.graduation.domain.usecase.main.user.create.task.AcceptTaskUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcceptTaskViewModel @Inject constructor(private val acceptTaskUseCase: AcceptTaskUseCase) :
    BaseViewModel() {

    private val _tasksResponse =
        MutableStateFlow<ResponseState<AcceptResponse>>(ResponseState.Empty())

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _tasksError = MutableLiveData<String>()
    val tasksError = _tasksError

    private val _tasksList =
        MutableLiveData<Task>()
    val tasksList = _tasksList

    private var _job: Job? = null


    fun callAcceptTask(role: String, applicationId: Int) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    acceptTaskUseCase.invoke(
                        role = role,
                        applicationId = applicationId
                    )
                }, onReply = {
                    _tasksResponse.emit(it)
                }, onToken = { _token })

        }
        validateTasksCall()
    }

    private fun validateTasksCall() {
        viewModelScope.launch {
            _tasksResponse.collect {
                when (it) {
                    is ResponseState.Empty -> {
                        _tasksError.value = "Categories is Empty"
                        Log.d("suzan", "Categories is Empty")
                    }

                    is ResponseState.NetworkError -> {
                        _tasksError.value =
                            "Check Internet Connection"
                        Log.d("suzan", "Check Internet Connection")

                    }

                    is ResponseState.Error -> {
                        _tasksError.value = it.message.toString()
                        Log.d("suzan", it.message.toString())

                    }

                    is ResponseState.Success -> {
                        _tasksError.value = Constants.VALID
                        _tasksList.value = it.data!!.data.task
                    }

                    is ResponseState.UnKnownError -> {
                        _tasksError.value = "UnKnownError"
                        Log.d("suzan", "UnKnownError")

                    }

                    is ResponseState.NotAuthorized -> {
                        _tasksError.value = "NotAuthorized"
                        Log.d("suzan", "NotAuthorized")

                    }

                    is ResponseState.Loading -> {
                        _tasksError.value = "loading"
                        Log.d("suzan", "loading")

                    }
                }
            }
        }
    }
}