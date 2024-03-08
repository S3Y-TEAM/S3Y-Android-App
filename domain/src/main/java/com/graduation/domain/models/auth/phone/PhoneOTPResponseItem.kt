package com.graduation.domain.models.auth.phone

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PhoneOTPResponseItem(
    @SerializedName("phone")
    val phone: Int,
)