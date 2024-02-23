package com.graduation.data.remote.dto.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class AuthResponseDto(
    @SerializedName("data")
    val data: AuthDataDto? = AuthDataDto(),
) : BaseResponse()

@Keep
data class AuthDataDto(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("user")
    val user: UserDto? = UserDto()
)

@Keep
data class UserDto(
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("country")
    val country: CountryDto? = CountryDto(),
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
data class CountryDto(
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
