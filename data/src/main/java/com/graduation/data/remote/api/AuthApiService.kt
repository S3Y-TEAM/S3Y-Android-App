package com.graduation.data.remote.api

import com.graduation.core.base.network.BaseResponse
import com.graduation.data.remote.interceptors.Authenticated
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.Auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.Auth.phone.PhoneOTPResponse
import com.graduation.domain.models.auth.Auth.username.UserNameRequest
import com.graduation.domain.models.auth.Auth.username.UsernameResponse
import com.graduation.domain.models.auth.otp.SendOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("users/sendOtp")
    suspend fun sendOtp(@Body otpRequest: SendOtpRequest): Response<BaseResponse>

//    @POST("users/auth")
//    suspend fun auth(
//        @Body authRequest: AuthRequest,
//    ): AuthResponseDto

    @POST("username")
    suspend fun username(
        @Header("role") role: String,
        @Body userNameRequest: UserNameRequest
    ): Response<UsernameResponse>

    @POST("categories")
    @Authenticated
    suspend fun categories(
        @Header("role") role: String,
        @Body categoriesRequest: UserNameRequest
    ) : Response<CategoriesResponse>

    @POST("email")
    @Authenticated
    suspend fun emailOtp(
        @Header("role") role: String,
        @Body emailOTPRequest: EmailOTPRequest
    ) : Response<EmailOTPResponse>

    @POST("phone")
    @Authenticated
    suspend fun phoneOtp(
        @Header("role") role: String,
        @Body phoneOTPRequest: PhoneOTPRequest
    ) : Response<PhoneOTPResponse>




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