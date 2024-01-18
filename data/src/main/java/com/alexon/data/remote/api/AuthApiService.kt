package com.alexon.data.remote.api

import com.alexon.core.AuthRequest
import com.alexon.core.SendOtpRequest
import com.alexon.core.base.BaseResponse
import com.alexon.data.remote.dto.auth.login.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("users/sendOtp")
    suspend fun sendOtp(@Body otpRequest: SendOtpRequest): Response<BaseResponse>

    @POST("users/auth")
    suspend fun auth(
        @Body authRequest: AuthRequest,
    ): AuthResponseDto


//    @POST("users/register")
//    @Multipart
//    suspend fun register(
//        @Query("device_token") device_token: String,
//        @Query("token") token: String,
//        @Query("name") name: String,
//        @Part image: MultipartBody.Part
//    ): Response<RegisterResponse>
//
//
//    @POST("users/checkOtp")
//    @HeadersSetup
//    suspend fun checkOtp(@Body checkOtpRequest: CheckOtpRequest): Response<CheckOtpResponse>
//
//    @POST("users/auth")
//    @HeadersSetup
//    suspend fun auth(@Body otpRequest: AuthRequest): Response<AuthResponse>

}