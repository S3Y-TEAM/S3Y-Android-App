package com.graduation.domain.repositories

import com.graduation.domain.models.auth.Auth.UserNameRequest
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.username.UsernameResponse
import retrofit2.Response

interface AuthRepository {

    //    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Response<BaseResponse>
//
//    suspend fun auth(authRequest: AuthRequest): AuthResponse
    suspend fun username(role: String, userNameRequest: UserNameRequest): Response<UsernameResponse>
    suspend fun categories(role: String, categoriesRequest: UserNameRequest): Response<CategoriesResponse>
}

