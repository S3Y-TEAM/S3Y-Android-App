package com.graduation.domain.usecase.main.dev

import com.graduation.domain.models.main.dev.tasks.TasksResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class TasksUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        employeeId: Int,
    ): Response<TasksResponse> =
        authRepository.getEmployeeTasks(role = role, employeeId = employeeId)

    suspend operator fun invoke(
        userId: Int,
    ): Response<TasksResponse> =
        authRepository.getUserTasks( userId = userId)

}