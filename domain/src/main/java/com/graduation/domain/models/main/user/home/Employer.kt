package com.graduation.domain.models.main.user.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Employer(
    @SerializedName("Address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Fname")
    val fname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Lname")
    val lname: String,
    @SerializedName("National_image")
    val nationalImage: Any,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Personal_image")
    val personalImage: PersonalImage,
    @SerializedName("Phone_number")
    val phoneNumber: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("user_name")
    val userName: String,
)