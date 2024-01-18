package com.alexon.domain.repositories

import com.alexon.core.AuthRequest
import com.alexon.domain.models.auth.login.AuthResponse

interface AuthRepository {

//    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Response<BaseResponse>

    suspend fun auth(authRequest: AuthRequest): AuthResponse
}

