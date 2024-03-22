package com.graduation.domain.usecase.main.dev

import com.graduation.domain.models.main.user.home.HomeResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        category: String,
    ): Response<HomeResponse> =
        authRepository.getHomeTasks(role = role, category = category)

}