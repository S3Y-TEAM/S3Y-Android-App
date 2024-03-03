package com.graduation.domain.usecase.auth

import com.graduation.domain.models.auth.phone.PhoneOTPRequest
import com.graduation.domain.models.auth.phone.PhoneOTPResponse
import com.graduation.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class PhoneOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        role: String,
        phoneOTPRequest: PhoneOTPRequest,
    ): Response<PhoneOTPResponse> =
        authRepository.phoneOTP(role = role, phoneOTPRequest = phoneOTPRequest)



}