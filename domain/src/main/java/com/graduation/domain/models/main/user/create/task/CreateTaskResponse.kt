package com.graduation.domain.models.main.user.create.task


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class CreateTaskResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()