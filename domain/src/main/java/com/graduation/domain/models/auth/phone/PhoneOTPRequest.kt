package com.graduation.domain.models.auth.phone

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PhoneOTPRequest(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
)