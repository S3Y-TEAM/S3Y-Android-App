package com.graduation.domain.usecase.main.user.create.task

import com.graduation.domain.models.main.user.accept.AcceptResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AcceptTaskUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        applicationId: Int,
    ): Response<AcceptResponse> =
        authRepository.acceptTask(role = role, applicationId = applicationId)

}