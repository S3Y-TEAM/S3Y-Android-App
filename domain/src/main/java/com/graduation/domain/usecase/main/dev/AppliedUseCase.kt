package com.graduation.domain.usecase.main.dev

import com.graduation.domain.models.main.dev.applied.AppliedResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AppliedUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        employeeId: Int,
    ): Response<AppliedResponse> =
        authRepository.getEmployeeApplied(role = role, employeeId = employeeId)

}