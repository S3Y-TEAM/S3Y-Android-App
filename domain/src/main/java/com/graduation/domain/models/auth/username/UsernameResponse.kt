package com.graduation.domain.models.auth.username

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class UsernameResponse(
    @SerializedName("data")
    val data: UsernameResponseItem,
) : BaseResponse()
