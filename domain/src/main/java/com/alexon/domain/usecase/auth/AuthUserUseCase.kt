package com.alexon.domain.usecase.auth

import com.alexon.core.AuthRequest
import com.alexon.core.base.ResponseState
import com.alexon.domain.models.auth.login.AuthResponse
import com.alexon.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface AuthUserUseCase {
    operator fun invoke(authRequest: AuthRequest): Flow<ResponseState<AuthResponse>>
}

