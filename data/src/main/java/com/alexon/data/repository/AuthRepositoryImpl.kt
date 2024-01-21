package com.alexon.data.repository

import com.alexon.domain.models.auth.Auth.AuthRequest
import com.alexon.data.mappers.toDomain
import com.alexon.data.remote.api.AuthApiService
import com.alexon.domain.models.auth.Auth.AuthResponse
import com.alexon.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
) : AuthRepository {

//    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): BaseResponse {
//        return authApiService.sendOtp(sendOtpRequest)
//    }

    override suspend fun auth(authRequest: AuthRequest): AuthResponse {
        return authApiService.auth(authRequest).toDomain()
    }

}