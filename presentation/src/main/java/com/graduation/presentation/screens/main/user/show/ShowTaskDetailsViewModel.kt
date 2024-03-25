package com.graduation.presentation.screens.main.user.show

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.main.user.create.task.details.Applicants
import com.graduation.domain.models.main.user.create.task.details.Task
import com.graduation.domain.models.main.user.create.task.details.TaskDetailsResponse
import com.graduation.domain.usecase.main.user.create.task.TaskDetailsUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowTaskDetailsViewModel @Inject constructor(private val taskDetailsUseCase: TaskDetailsUseCase) :
    BaseViewModel() {

    private val _tasksResponse =
        MutableStateFlow<ResponseState<TaskDetailsResponse>>(ResponseState.Empty())

    private var _token = MutableLiveData<String>()
    val token = _token

    private var _tasksError = MutableLiveData<String>()
    val tasksError = _tasksError

    private val _tasksList =
        MutableLiveData<Applicants>()
    val tasksList = _tasksList

    private val _taskData =
        MutableLiveData<Task>()
    val taskData = _taskData

    private var _job: Job? = null


    fun callTaskDetails(role: String, taskId: Int) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    taskDetailsUseCase.invoke(
                        role = role,
                        taskId = taskId
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
                        _tasksList.value = it.data!!.data.task.applicants
                        _taskData.value = it.data!!.data.task
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
