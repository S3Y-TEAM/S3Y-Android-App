package com.alexon.data.repository

import com.alexon.core.base.ResponseState
import com.alexon.data.remote.api.AuthApiService
import com.alexon.data.remote.dto.login.LoginResponse
import com.alexon.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
) : AuthRepository {
    override suspend fun login(): ResponseState<LoginResponse> {
        authApiService.log
    }
}