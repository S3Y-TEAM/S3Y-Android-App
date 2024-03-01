package com.graduation.domain.models.auth.Auth.phone

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.auth.Auth.email.EmailOTPResponseItem
@Keep
data class PhoneOTPResponse (
    @SerializedName("data")
    val data: PhoneOTPResponseItem,
) : BaseResponse()