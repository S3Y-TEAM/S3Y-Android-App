package com.graduation.domain.usecase.main.user.create.task

import com.graduation.domain.models.main.user.create.task.CreateTaskResponse
import com.graduation.domain.repositories.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        role: String,
        title: RequestBody,
        description: RequestBody,
        categories: RequestBody,
        employerId: RequestBody,
        date: RequestBody,
        deadline: RequestBody,
        price: RequestBody,
        address: RequestBody,
        file: MultipartBody.Part,
    ): Response<CreateTaskResponse> =
        authRepository.createTask(
            role = role,
            title = title,
            description = description,
            categories = categories,
            employerId = employerId,
            date = date,
            deadline = deadline,
            price = price,
            address = address,
            file = file
        )

}