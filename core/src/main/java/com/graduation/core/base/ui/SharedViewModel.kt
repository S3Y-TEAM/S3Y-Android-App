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

    private val _github =
        MutableLiveData<String>()
    val github = _github

    private val _linkedIn =
        MutableLiveData<String>()
    val linkedIn = _linkedIn

    private val _emailAddress =
        MutableLiveData<String>()
    val emailAddress = _emailAddress

    private val _phoneNumber =
        MutableLiveData<Int>()
    val phoneNumber = _phoneNumber

    private val _country =
        MutableLiveData<String>()
    val country = _country

    private val _city =
        MutableLiveData<String>()
    val city = _city

    private val _address =
        MutableLiveData<String>()
    val address = _address

    private val _chosenCategories =
        MutableLiveData<ArrayList<String>>()
    val chosenCategories = _chosenCategories

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
        _experience.value = experience
    }

    fun setGitHub(github: String) = saveGitHub(github = github)

    private fun saveGitHub(github: String) {
        _github.value = github
    }

    fun setLinkedIn(linkedIn: String) = saveLinkedIn(linkedIn = linkedIn)

    private fun saveLinkedIn(linkedIn: String) {
        _linkedIn.value = linkedIn
    }

    fun setRole(role: String) = saveRole(role = role)

    private fun saveRole(role: String) {
        _role.value = role
    }

    fun setEmailAddress(emailAddress: String) = saveEmailAddress(emailAddress = emailAddress)

    private fun saveEmailAddress(emailAddress: String) {
        _emailAddress.value = emailAddress
    }

    fun setPhoneNumber(phoneNumber: Int) = savePhoneNumber(phoneNumber = phoneNumber)

    private fun savePhoneNumber(phoneNumber: Int) {
        _phoneNumber.value = phoneNumber
    }

    fun setCity(city: String) = saveCity(city = city)

    private fun saveCity(city: String) {
        _city.value = city
    }

    fun setCountry(country: String) = saveCountry(country = country)

    private fun saveCountry(country: String) {
        _country.value = country
    }

    fun setAddress(address: String) = saveAddress(address = address)

    private fun saveAddress(address: String) {
        _address.value = address
    }

    fun setSavedCategories(savedCategories: ArrayList<Int>) =
        savedCategories(savedCategories = savedCategories)

    private fun savedCategories(savedCategories: ArrayList<Int>) {
        _savedCategories.value = savedCategories
    }

    fun setChosenCategories(savedChosenCategories: ArrayList<String>) =
        savedChosenCategories(savedChosenCategories = savedChosenCategories)

    private fun savedChosenCategories(savedChosenCategories: ArrayList<String>) {
        _chosenCategories.value = savedChosenCategories
    }


}