package com.graduation.data.repositoryImpl

import com.graduation.domain.models.auth.Auth.AuthRequest
import com.graduation.data.mappers.toDomain
import com.graduation.data.remote.api.AuthApiService
import com.graduation.domain.models.auth.Auth.AuthResponse
import com.graduation.domain.repositories.AuthRepository

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