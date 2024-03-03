package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.forgetpassword.ForgetPasswordRequest
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        forgetPasswordRequest: ForgetPasswordRequest,
    ): Response<EmailOTPResponse> =
        authRepository.forgetPassword(role = role, forgetPasswordRequest = forgetPasswordRequest)

}