package com.graduation.domain.usecase.auth

import com.graduation.domain.repositories.AuthRepository

class AuthUserUseCase(
    private val authRepository: AuthRepository
) {
//    suspend operator fun invoke(authRequest: AuthRequest): ResponseState<AuthResponse> =
//        authRepository.auth(authRequest)


}

