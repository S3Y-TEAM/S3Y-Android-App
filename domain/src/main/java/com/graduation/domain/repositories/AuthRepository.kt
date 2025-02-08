package com.graduation.domain.repositories

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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface AuthRepository {

    //    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Response<BaseResponse>
//
//    suspend fun auth(authRequest: AuthRequest): AuthResponse
    suspend fun username(role: String, userNameRequest: UserNameRequest): Response<UsernameResponse>
    suspend fun categories(
        role: String,
        categoriesRequest: UserNameRequest,
    ): Response<CategoriesResponse>

    suspend fun emailOTP(role: String, emailOTPRequest: EmailOTPRequest): Response<EmailOTPResponse>
    suspend fun phoneOTP(role: String, phoneOTPRequest: PhoneOTPRequest): Response<PhoneOTPResponse>
    suspend fun signUp(role: String, signupRequest: SignUpRequest): Response<SignUpResponse>
    suspend fun login(role: String, loginRequest: LoginRequest): Response<LoginResponse>
    suspend fun forgetPassword(
        role: String,
        forgetPasswordRequest: ForgetPasswordRequest,
    ): Response<EmailOTPResponse>

    suspend fun resetPassword(
        role: String,
        resetPasswordRequest: ResetPasswordRequest,
    ): Response<ResetPasswordResponse>

    suspend fun getEmployeeTasks(role: String, employeeId: Int): Response<TasksResponse>
    suspend fun getUserTasks(userId: Int): Response<TasksResponse>
    suspend fun getEmployeeApplied(role: String, employeeId: Int): Response<AppliedResponse>
    suspend fun createTask(
        role: String, title: RequestBody,
        description: RequestBody,
        categories: RequestBody,
        employerId: RequestBody,
        date: RequestBody,
        deadline: RequestBody,
        price: RequestBody,
        address: RequestBody,
        file: MultipartBody.Part,
    ): Response<CreateTaskResponse>

    suspend fun getHomeTasks(role: String, category: String): Response<HomeResponse>

    suspend fun applyForTask(role: String, taskId: Int , applyRequest: ApplyRequest): Response<ApplyResponse>
    suspend fun getTaskDetails(role: String, taskId: Int ): Response<TaskDetailsResponse>
    suspend fun acceptTask(role: String, applicationId: Int ): Response<AcceptResponse>



}

