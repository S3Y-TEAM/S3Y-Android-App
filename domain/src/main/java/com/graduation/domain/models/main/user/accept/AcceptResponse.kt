package com.graduation.domain.models.main.user.accept


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class AcceptResponse(
    @SerializedName("data")
    val `data`: Data,
) : BaseResponse()