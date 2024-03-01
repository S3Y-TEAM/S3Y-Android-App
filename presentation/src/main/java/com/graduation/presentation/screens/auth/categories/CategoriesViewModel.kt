package com.graduation.presentation.screens.auth.categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponseItem
import com.graduation.domain.models.auth.Auth.username.UserNameRequest
import com.graduation.domain.usecase.auth.CategoriesUseCase
import com.graduation.presentation.Constants.VALID
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

    private var _categoriesError= MutableLiveData<String>()
    val categoriesError = _categoriesError

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
        validateCategoriesCall()
    }

    private fun validateCategoriesCall() {
        viewModelScope.launch {
            _categoriesResponse.collect {
                when (it) {
                    is ResponseState.Empty -> _categoriesError.value =  "Categories is Empty"
                    is ResponseState.NetworkError -> _categoriesError.value = "Check Internet Connection"
                    is ResponseState.Error -> _categoriesError.value = it.message.toString()
                    is ResponseState.Success -> {
                        _categoriesError.value = VALID
                        _categoriesList.value = it.data!!.data
                    }

                    is ResponseState.UnKnownError -> _categoriesError.value = "UnKnownError"
                    is ResponseState.NotAuthorized -> _categoriesError.value = "NotAuthorized"
                    is ResponseState.Loading -> _categoriesError.value = "loading"
                }
            }
        }
    }


}