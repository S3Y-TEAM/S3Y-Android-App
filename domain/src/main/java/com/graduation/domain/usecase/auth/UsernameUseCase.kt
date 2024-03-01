package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.Auth.username.UserNameRequest
import com.graduation.domain.models.auth.Auth.username.UsernameResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class UsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        userNameRequest: UserNameRequest,
    ): Response<UsernameResponse> =
        authRepository.username(role = role, userNameRequest = userNameRequest)

}

