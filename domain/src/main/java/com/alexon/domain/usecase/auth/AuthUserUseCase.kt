package com.alexon.domain.usecase.auth

import com.alexon.domain.models.auth.Auth.AuthRequest
import com.alexon.core.base.ResponseState
import com.alexon.domain.models.auth.Auth.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthUserUseCase {
    operator fun invoke(authRequest: AuthRequest): Flow<ResponseState<AuthResponse>>
}

