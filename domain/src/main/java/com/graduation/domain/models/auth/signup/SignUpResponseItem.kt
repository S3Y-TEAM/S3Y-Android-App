package com.graduation.domain.models.auth.signup

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignUpResponseItem(
    @SerializedName("user")
    val user: User,
)

@Keep
data class User(
    @SerializedName("Address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Fname")
    val fName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Lname")
    val lName: String,
    @SerializedName("National_id")
    val nationalId: String,
    @SerializedName("Personal_image")
    val personalImage: String,
    @SerializedName("Phone_number")
    val phoneNumber: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("verified")
    val verified: Int,
)