package com.graduation.domain.models.main.user.create.task.details


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.main.user.create.task.details.Data

@Keep
data class TaskDetailsResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()