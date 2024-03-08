package com.graduation.domain.models.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequest(
    @SerializedName("email")
    val email : String ,
    @SerializedName("password")
    val password : String ,
)
