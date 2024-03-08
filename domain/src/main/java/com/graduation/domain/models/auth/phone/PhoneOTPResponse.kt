package com.graduation.domain.models.auth.phone

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class PhoneOTPResponse (
    @SerializedName("data")
    val data: PhoneOTPResponseItem,
) : BaseResponse()