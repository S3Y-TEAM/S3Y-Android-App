package com.graduation.domain.models.auth.forgetpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgetPasswordRequest(
    @SerializedName("email")
    val email: String,
)