package com.graduation.data.repositoryImpl

import com.graduation.data.remote.api.AuthApiService
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
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {

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

    override suspend fun signUp(
        role: String,
        signupRequest: SignUpRequest,
    ): Response<SignUpResponse> =
        authApiService.signUp(role = role, signupRequest = signupRequest)

    override suspend fun login(role: String, loginRequest: LoginRequest): Response<LoginResponse> =
        authApiService.login(role = role, loginRequest = loginRequest)

    override suspend fun forgetPassword(
        role: String,
        forgetPasswordRequest: ForgetPasswordRequest,
    ): Response<EmailOTPResponse> =
        authApiService.forgetPassword(role = role, forgetPasswordRequest = forgetPasswordRequest)

    override suspend fun resetPassword(
        role: String,
        resetPasswordRequest: ResetPasswordRequest,
    ): Response<ResetPasswordResponse> =
        authApiService.resetPassword(role = role, resetPasswordRequest = resetPasswordRequest)

}