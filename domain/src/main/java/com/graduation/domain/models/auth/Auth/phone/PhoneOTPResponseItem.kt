package com.graduation.domain.models.auth.Auth.phone

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PhoneOTPResponseItem(
    @SerializedName("phone")
    val phone: Int,
)