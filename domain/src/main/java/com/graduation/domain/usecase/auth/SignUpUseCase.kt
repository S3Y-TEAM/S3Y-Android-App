package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.signup.SignUpRequest
import com.graduation.domain.models.auth.signup.SignUpResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        signUpRequest: SignUpRequest,
    ): Response<SignUpResponse> =
        authRepository.signUp(role = role, signupRequest = signUpRequest)

}