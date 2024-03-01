package com.graduation.data.repositoryImpl

import com.graduation.data.remote.api.AuthApiService
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.Auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPResponse
import com.graduation.domain.models.auth.Auth.username.UserNameRequest
import com.graduation.domain.models.auth.Auth.username.UsernameResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {

//    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): BaseResponse {
//        return authApiService.sendOtp(sendOtpRequest)
//    }

//    override suspend fun auth(authRequest: AuthRequest): AuthResponse {
//        return authApiService.auth(authRequest).toDomain()
//    }

    override suspend fun username(
        role: String,
        userNameRequest: UserNameRequest,
    ): Response<UsernameResponse> =
        authApiService.username(role = role, userNameRequest = userNameRequest)

    override suspend fun categories(
        role: String,
        categoriesRequest: UserNameRequest,
    ): Response<CategoriesResponse> =
        authApiService.categories(
            role = role, categoriesRequest = categoriesRequest
        )

    override suspend fun emailOTP(
        role: String,
        emailOTPRequest: EmailOTPRequest,
    ): Response<EmailOTPResponse> =
        authApiService.emailOtp(role = role, emailOTPRequest = emailOTPRequest)

    override suspend fun phoneOTP(
        role: String,
        phoneOTPRequest: PhoneOTPRequest,
    ): Response<PhoneOTPResponse> =
        authApiService.phoneOtp(role = role, phoneOTPRequest = phoneOTPRequest)

}