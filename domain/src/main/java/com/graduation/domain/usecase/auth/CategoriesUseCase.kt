package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.Auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.Auth.username.UserNameRequest
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        categoriesRequest: UserNameRequest,
    ): Response<CategoriesResponse> =
        authRepository.categories(role = role, categoriesRequest = categoriesRequest)

}