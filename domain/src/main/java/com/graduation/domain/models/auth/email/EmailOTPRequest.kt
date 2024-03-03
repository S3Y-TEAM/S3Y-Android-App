package com.graduation.domain.models.auth.email

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EmailOTPRequest(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
)