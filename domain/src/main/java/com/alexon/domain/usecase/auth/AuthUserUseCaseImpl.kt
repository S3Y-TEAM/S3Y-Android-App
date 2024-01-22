package com.alexon.domain.usecase.auth

import com.alexon.domain.models.auth.Auth.AuthRequest
import com.alexon.core.base.network.ResponseState
import com.alexon.domain.models.auth.Auth.AuthResponse
import com.alexon.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthUserUseCaseImpl (private val authRepository: AuthRepository)  : AuthUserUseCase{
    override operator fun invoke(authRequest: AuthRequest): Flow<ResponseState<AuthResponse>> = flow {
        emit(ResponseState.Loading())
        try {
            emit(
                ResponseState.Success(
                    data = authRepository.auth(authRequest)
                )
            )
        } catch (e: Exception) {
            emit(
                ResponseState.Error(
                    message = e.message.toString(),
                    errors = null
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}

