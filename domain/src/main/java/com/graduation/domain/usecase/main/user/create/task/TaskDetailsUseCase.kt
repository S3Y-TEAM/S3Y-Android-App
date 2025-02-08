package com.graduation.domain.usecase.main.user.create.task

import com.graduation.domain.models.main.user.create.task.details.TaskDetailsResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class TaskDetailsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        taskId: Int,
    ): Response<TaskDetailsResponse> =
        authRepository.getTaskDetails(role = role, taskId = taskId)

}