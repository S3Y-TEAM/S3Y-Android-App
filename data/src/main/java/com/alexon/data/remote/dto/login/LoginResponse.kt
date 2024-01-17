package com.alexon.data.remote.dto.login

import androidx.annotation.Keep
import com.alexon.core.base.BaseResponse

@Keep
data class LoginResponse(
    val data: LoginData?,
) : BaseResponse()

@Keep
data class LoginData(
    val user: UserData?
)

@Keep
data class UserData(
    val address: String?,
    val country_code: String?,
    val currency: String?,
    val id: Int?,
    val image: String?,
    val is_verified: Int?,
    val language: String?,
    val name: String?,
    val newCountryCode: String?,
    val newPhone: String?,
    val phone: String?
)
