package com.alexon.data.remote.dto.login

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val phone: String,
    val country_code: String,
    val device_token: String,
)
