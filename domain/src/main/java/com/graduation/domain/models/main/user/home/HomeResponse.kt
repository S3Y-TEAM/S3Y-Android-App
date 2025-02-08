package com.graduation.domain.models.main.user.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.main.user.home.Data

@Keep
data class HomeResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()