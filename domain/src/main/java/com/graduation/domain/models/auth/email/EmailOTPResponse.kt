package com.graduation.domain.models.auth.email

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class EmailOTPResponse(
    @SerializedName("data")
    val data: EmailOTPResponseItem,
) : BaseResponse()