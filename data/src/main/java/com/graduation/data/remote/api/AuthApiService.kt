package com.graduation.data.remote.api

import com.graduation.data.remote.interceptors.Authenticated
import com.graduation.domain.models.auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.forgetpassword.ForgetPasswordRequest
import com.graduation.domain.models.auth.login.LoginRequest
import com.graduation.domain.models.auth.login.LoginResponse
import com.graduation.domain.models.auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.phone.PhoneOTPResponse
import com.graduation.domain.models.auth.resetpassword.ResetPasswordRequest
import com.graduation.domain.models.auth.resetpassword.ResetPasswordResponse
import com.graduation.domain.models.auth.signup.SignUpRequest
import com.graduation.domain.models.auth.signup.SignUpResponse
import com.graduation.domain.models.auth.username.UserNameRequest
import com.graduation.domain.models.auth.username.UsernameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("username")
    suspend fun username(
        @Header("role") role: String,
        @Body userNameRequest: UserNameRequest,
    ): Response<UsernameResponse>

    @POST("categories")
    @Authenticated
    suspend fun categories(
        @Header("role") role: String,
        @Body categoriesRequest: UserNameRequest,
    ): Response<CategoriesResponse>

    @POST("email")
    @Authenticated
    suspend fun emailOtp(
        @Header("role") role: String,
        @Body emailOTPRequest: EmailOTPRequest,
    ): Response<EmailOTPResponse>

    @POST("phone")
    @Authenticated
    suspend fun phoneOtp(
        @Header("role") role: String,
        @Body phoneOTPRequest: PhoneOTPRequest,
    ): Response<PhoneOTPResponse>

    @POST("signup")
    @Authenticated
    suspend fun signUp(
        @Header("role") role: String,
        @Body signupRequest: SignUpRequest,
    ): Response<SignUpResponse>

    @POST("signin")
    suspend fun login(
        @Header("role") role: String,
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>

    @POST("forgetpassword")
    suspend fun forgetPassword(
        @Header("role") role: String,
        @Body forgetPasswordRequest: ForgetPasswordRequest,
    ): Response<EmailOTPResponse>

    @POST("resetpassword")
    @Authenticated
    suspend fun resetPassword(
        @Header("role") role: String,
        @Body resetPasswordRequest: ResetPasswordRequest,
    ): Response<ResetPasswordResponse>

}