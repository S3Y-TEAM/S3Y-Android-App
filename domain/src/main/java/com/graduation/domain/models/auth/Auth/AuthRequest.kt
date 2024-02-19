package com.graduation.domain.models.auth.Auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthRequest(
    val phone : String ,
    @SerializedName("country_id")
    val countryId : Int ,
    val otp : String,
)


@Keep
data class UserNameRequest(
    @SerializedName("userName")
    val userName : String
)
