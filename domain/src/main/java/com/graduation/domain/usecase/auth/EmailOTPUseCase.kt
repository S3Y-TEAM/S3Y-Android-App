package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.Auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.Auth.email.EmailOTPResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class EmailOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        emailOTPRequest: EmailOTPRequest,
    ): Response<EmailOTPResponse> =
        authRepository.emailOTP(role = role, emailOTPRequest = emailOTPRequest)

}