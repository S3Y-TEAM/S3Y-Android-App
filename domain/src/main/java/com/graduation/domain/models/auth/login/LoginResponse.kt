package com.graduation.domain.models.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.auth.signup.User

@Keep
data class LoginResponse(
    @SerializedName("data")
    var data: User,
) : BaseResponse()

//@Keep
//data class User(
//    @SerializedName("address")
//    val address: String? = "",
//    @SerializedName("country")
//    val country: Country? = Country(),
//    @SerializedName("id")
//    val id: Int? = 0,
//    @SerializedName("image")
//    val image: String? = "",
//    @SerializedName("is_verified")
//    val isVerified: Int? = 0,
//    @SerializedName("language")
//    val language: String? = "",
//    @SerializedName("name")
//    val name: String? = "",
//    @SerializedName("phone")
//    val phone: String? = "",
//    @SerializedName("token")
//    val token: String? = ""
//)
//
//@Keep
//data class Country(
//    @SerializedName("code")
//    val code: String? = "",
//    @SerializedName("currency")
//    val currency: String? = "",
//    @SerializedName("flag")
//    val flag: String? = "",
//    @SerializedName("max_number")
//    val maxNumber: Int? = 0,
//    @SerializedName("name")
//    val name: String? = ""
//)
