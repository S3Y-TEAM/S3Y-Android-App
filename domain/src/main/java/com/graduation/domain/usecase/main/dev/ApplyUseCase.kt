package com.graduation.domain.usecase.main.dev

import com.graduation.domain.models.main.dev.applied.AppliedResponse
import com.graduation.domain.models.main.dev.apply.ApplyRequest
import com.graduation.domain.models.main.dev.apply.ApplyResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class ApplyUseCase@Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        taskId: Int,
        applyRequest: ApplyRequest,
    ): Response<ApplyResponse> =
        authRepository.applyForTask(role = role, taskId = taskId, applyRequest = applyRequest)

}