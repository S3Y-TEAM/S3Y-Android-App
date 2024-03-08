package com.graduation.domain.models.auth.resetpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResetPasswordRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String,
)