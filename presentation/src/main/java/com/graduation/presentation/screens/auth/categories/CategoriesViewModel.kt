package com.graduation.presentation.screens.auth.categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.Auth.UserNameRequest
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponseItem
import com.graduation.domain.usecase.auth.CategoriesUseCase
import com.graduation.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CategoriesViewModel @Inject constructor(private val categoriesUseCase: CategoriesUseCase) :
    BaseViewModel() {

    private val _categoriesResponse =
        MutableStateFlow<ResponseState<CategoriesResponse>>(ResponseState.Empty())
    val categoriesResponse = _categoriesResponse.asStateFlow()

    private var _token = MutableLiveData<String>()
    val token = _token

    private val _categoriesList =
        MutableLiveData<List<CategoriesResponseItem>>()
    val categoriesList = _categoriesList

    private var _job: Job? = null


    fun callCategories(role: String, categoriesRequest: UserNameRequest) {

        viewModelScope.launch {
            _job =
                networkCall(action = {
                    categoriesUseCase.invoke(
                        role = role,
                        categoriesRequest = categoriesRequest
                    )
                }, onReply = {
                    _categoriesResponse.emit(it)
                }, onToken = { _token })


        }
        validateUserName()
    }

    private fun validateUserName() {
        viewModelScope.launch {
            _categoriesResponse.collect {
                when (it) {
                    is ResponseState.Empty -> Log.d("suzan", "User Name is Empty")
                    is ResponseState.NetworkError -> Log.d("suzan", "Check Internet Connection")
                    is ResponseState.Error -> Log.d("suzan", it.message.toString())
                    is ResponseState.Success -> {
                        Log.d("responseSuccess", it.data!!.data.toString())
                        _categoriesList.value = it.data!!.data
                    }

                    is ResponseState.UnKnownError -> Log.d("suzan", "UnKnownError")
                    is ResponseState.NotAuthorized -> Log.d("suzan", "NotAuthorized")
                    is ResponseState.Loading -> Log.d("suzan", "loading")
                }
            }
        }
    }


}