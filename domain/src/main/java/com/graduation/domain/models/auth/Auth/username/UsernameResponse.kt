package com.graduation.domain.models.auth.Auth.username

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class UsernameResponse(
    @SerializedName("data")
    val data: Data,
) : BaseResponse()
