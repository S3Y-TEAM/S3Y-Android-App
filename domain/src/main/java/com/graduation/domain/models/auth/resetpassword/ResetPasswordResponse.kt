package com.graduation.domain.models.auth.resetpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class ResetPasswordResponse(
    @SerializedName("data")
    val data: Any,
) : BaseResponse()
