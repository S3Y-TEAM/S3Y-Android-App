package com.graduation.domain.models.main.dev.apply


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.main.dev.apply.Data

@Keep
data class ApplyResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()