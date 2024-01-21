package com.alexon.domain.repositories

import com.alexon.domain.models.auth.Auth.AuthRequest
import com.alexon.domain.models.auth.Auth.AuthResponse

interface AuthRepository {

//    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Response<BaseResponse>

    suspend fun auth(authRequest: AuthRequest): AuthResponse
}

