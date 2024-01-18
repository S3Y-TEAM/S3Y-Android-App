package com.alexon.core

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SendOtpRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("country_id")
    val countryId: Int,
)