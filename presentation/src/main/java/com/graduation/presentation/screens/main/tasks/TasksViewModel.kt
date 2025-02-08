package com.graduation.presentation.screens.main.tasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.main.dev.tasks.Task
import com.graduation.domain.models.main.dev.tasks.TasksResponse
import com.graduation.domain.usecase.main.dev.TasksUseCase
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.USER_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val tasksUseCase: TasksUseCase) :
    BaseViewModel() {

    private val _tasksResponse =
        MutableStateFlow<ResponseState<TasksResponse>>(ResponseState.Empty())

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _tasksError = MutableLiveData<String>()
    val tasksError = _tasksError

    private val _tasksList =
        MutableLiveData<List<Task>>()
    val tasksList = _tasksList

    private var _job: Job? = null


    fun callDevTasks(role: String, employeeId: Int) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    tasksUseCase.invoke(
                        role = role,
                        employeeId = employeeId
                    )
                }, onReply = {
                    _tasksResponse.emit(it)
                }, onToken = { _token })


        }
        validateTasksCall()
    }

    fun callUserTasks(userId: Int) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    tasksUseCase.invoke(
                        userId = userId
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
                        _tasksList.value = it.data!!.data.tasks
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