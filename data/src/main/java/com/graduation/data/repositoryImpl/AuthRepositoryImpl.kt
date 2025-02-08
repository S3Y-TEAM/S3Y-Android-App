package com.graduation.data.repositoryImpl

import com.graduation.data.remote.api.AuthApiService
import com.graduation.domain.models.auth.categories.CategoriesResponse
import com.graduation.domain.models.auth.email.EmailOTPRequest
import com.graduation.domain.models.auth.email.EmailOTPResponse
import com.graduation.domain.models.auth.forgetpassword.ForgetPasswordRequest
import com.graduation.domain.models.auth.login.LoginRequest
import com.graduation.domain.models.auth.login.LoginResponse
import com.graduation.domain.models.auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.phone.PhoneOTPResponse
import com.graduation.domain.models.auth.resetpassword.ResetPasswordRequest
import com.graduation.domain.models.auth.resetpassword.ResetPasswordResponse
import com.graduation.domain.models.auth.signup.SignUpRequest
import com.graduation.domain.models.auth.signup.SignUpResponse
import com.graduation.domain.models.auth.username.UserNameRequest
import com.graduation.domain.models.auth.username.UsernameResponse
import com.graduation.domain.models.main.dev.applied.AppliedResponse
import com.graduation.domain.models.main.dev.apply.ApplyRequest
import com.graduation.domain.models.main.dev.apply.ApplyResponse
import com.graduation.domain.models.main.dev.tasks.TasksResponse
import com.graduation.domain.models.main.user.accept.AcceptResponse
import com.graduation.domain.models.main.user.create.task.CreateTaskResponse
import com.graduation.domain.models.main.user.create.task.details.TaskDetailsResponse
import com.graduation.domain.models.main.user.home.HomeResponse
import com.graduation.domain.repositories.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {

    override suspend fun username(
        role: String,
        userNameRequest: UserNameRequest,
    ): Response<UsernameResponse> =
        authApiService.username(role = role, userNameRequest = userNameRequest)

    override suspend fun categories(
        role: String,
        categoriesRequest: UserNameRequest,
    ): Response<CategoriesResponse> =
        authApiService.categories(
            role = role, categoriesRequest = categoriesRequest
        )

    override suspend fun emailOTP(
        role: String,
        emailOTPRequest: EmailOTPRequest,
    ): Response<EmailOTPResponse> =
        authApiService.emailOtp(role = role, emailOTPRequest = emailOTPRequest)

    override suspend fun phoneOTP(
        role: String,
        phoneOTPRequest: PhoneOTPRequest,
    ): Response<PhoneOTPResponse> =
        authApiService.phoneOtp(role = role, phoneOTPRequest = phoneOTPRequest)

    override suspend fun signUp(
        role: String,
        signupRequest: SignUpRequest,
    ): Response<SignUpResponse> =
        authApiService.signUp(role = role, signupRequest = signupRequest)

    override suspend fun login(role: String, loginRequest: LoginRequest): Response<LoginResponse> =
        authApiService.login(role = role, loginRequest = loginRequest)

    override suspend fun forgetPassword(
        role: String,
        forgetPasswordRequest: ForgetPasswordRequest,
    ): Response<EmailOTPResponse> =
        authApiService.forgetPassword(role = role, forgetPasswordRequest = forgetPasswordRequest)

    override suspend fun resetPassword(
        role: String,
        resetPasswordRequest: ResetPasswordRequest,
    ): Response<ResetPasswordResponse> =
        authApiService.resetPassword(role = role, resetPasswordRequest = resetPasswordRequest)

    override suspend fun getEmployeeTasks(
        role: String,
        employeeId: Int,
    ): Response<TasksResponse> =
        authApiService.getEmployeeTasks(role = role, employeeId = employeeId)

    override suspend fun getUserTasks(userId: Int): Response<TasksResponse> =
        authApiService.getUserTasks(employerId = userId)

    override suspend fun getEmployeeApplied(
        role: String,
        employeeId: Int,
    ): Response<AppliedResponse> =
        authApiService.getEmployeeApplied(role = role, employeeId = employeeId)

    override suspend fun createTask(
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
    ): Response<CreateTaskResponse> = authApiService.createTask(
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

    override suspend fun getHomeTasks(
        role: String,
        category: String,
    ): Response<HomeResponse> =
        authApiService.homeTasks(role = role, category = category)

    override suspend fun applyForTask(
        role: String,
        taskId: Int,
        applyRequest: ApplyRequest,
    ): Response<ApplyResponse> =
        authApiService.applyForTask(role = role, taskId = taskId, requestBody = applyRequest)

    override suspend fun getTaskDetails(role: String, taskId: Int): Response<TaskDetailsResponse> =
        authApiService.taskDetails(role = role, taskId = taskId)

    override suspend fun acceptTask(role: String, applicationId: Int): Response<AcceptResponse> =
        authApiService.acceptTask(role = role , applicationId = applicationId)


}