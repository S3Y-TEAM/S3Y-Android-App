package com.alexon.domain.models.auth.Auth

import androidx.annotation.Keep
import com.alexon.core.base.network.BaseResponse
import com.google.gson.annotations.SerializedName

@Keep
data class AuthResponse(
    @SerializedName("data")
    var data: AuthData? = AuthData(),
) : BaseResponse()

@Keep
data class AuthData(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("user")
    val user: User? = User()
)

@Keep
data class User(
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("country")
    val country: Country? = Country(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("is_verified")
    val isVerified: Int? = 0,
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("token")
    val token: String? = ""
)

@Keep
data class Country(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("currency")
    val currency: String? = "",
    @SerializedName("flag")
    val flag: String? = "",
    @SerializedName("max_number")
    val maxNumber: Int? = 0,
    @SerializedName("name")
    val name: String? = ""
)
