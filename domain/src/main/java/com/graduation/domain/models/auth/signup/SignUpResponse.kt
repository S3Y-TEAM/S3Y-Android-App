package com.graduation.domain.models.auth.signup

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
@Keep
data class SignUpResponse(
    @SerializedName("data")
    val data: SignUpResponseItem,
) : BaseResponse()
