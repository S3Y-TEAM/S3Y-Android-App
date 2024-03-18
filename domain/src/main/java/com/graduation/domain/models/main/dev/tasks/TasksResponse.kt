package com.graduation.domain.models.main.dev.tasks


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class TasksResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()