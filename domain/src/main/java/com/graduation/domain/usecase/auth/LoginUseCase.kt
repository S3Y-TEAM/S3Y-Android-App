package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.login.LoginRequest
import com.graduation.domain.models.auth.login.LoginResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        loginRequest: LoginRequest,
    ): Response<LoginResponse> =
        authRepository.login(role = role, loginRequest = loginRequest)

}