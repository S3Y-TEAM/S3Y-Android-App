package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.resetpassword.ResetPasswordRequest
import com.graduation.domain.models.auth.resetpassword.ResetPasswordResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        resetPasswordRequest: ResetPasswordRequest,
    ): Response<ResetPasswordResponse> =
        authRepository.resetPassword(role = role, resetPasswordRequest = resetPasswordRequest)

}