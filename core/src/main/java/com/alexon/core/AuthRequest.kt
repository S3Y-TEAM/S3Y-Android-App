package com.alexon.core

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthRequest(
    val phone : String ,
    @SerializedName("country_id")
    val countryId : Int ,
    val otp : String,
)