package com.graduation.domain.models.auth.Auth.email

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EmailOTPResponseItem (
    @SerializedName("codeNumber")
    val codeNumber: Int
)