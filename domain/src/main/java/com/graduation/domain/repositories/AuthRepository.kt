package com.graduation.domain.repositories

import com.graduation.domain.models.auth.Auth.AuthRequest
import com.graduation.domain.models.auth.Auth.AuthResponse

interface AuthRepository {

//    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Response<BaseResponse>

    suspend fun auth(authRequest: AuthRequest): AuthResponse
}

