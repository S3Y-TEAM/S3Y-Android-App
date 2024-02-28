package com.graduation.core.base.ui

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class SharedViewModel @Inject constructor() : BaseViewModel() {

    private val _username =
        MutableLiveData<String>()
    val username = _username

    private val _fullName =
        MutableLiveData<String>()
    val fullName = _fullName

    private val _password =
        MutableLiveData<String>()
    val password = _password

    private val _role =
        MutableLiveData<String>()
    val role = _role

    private val _experience =
        MutableLiveData<String>()
    val experience = _experience

    private val _savedCategories =
        MutableLiveData<ArrayList<Int>>()
    val savedCategories = _savedCategories

    fun setFullName(fullName: String) = saveFullName(fullName = fullName)

    private fun saveFullName(fullName: String) {
        _fullName.value = fullName
    }

    fun setPassword(password: String) = savePassword(password = password)

    private fun savePassword(password: String) {
        _password.value = password
    }

    fun setUsername(username: String) = saveUserName(username = username)

    private fun saveUserName(username: String) {
        _username.value = username
    }

    fun setExperience(experience: String) = saveExperience(experience = experience)

    private fun saveExperience(experience: String) {
        _role.value = experience
    }

    fun setRole(role: String) = saveRole(role = role)

    private fun saveRole(role: String) {
        _role.value = role
    }

    fun setSavedCategories(savedCategories: ArrayList<Int>) =
        savedCategories(savedCategories = savedCategories)

    private fun savedCategories(savedCategories: ArrayList<Int>) {
        _savedCategories.value = savedCategories
    }


}