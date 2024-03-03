package com.graduation.domain.models.auth.categories

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse

@Keep
data class CategoriesResponse(
    @SerializedName("data")
    val data: List<CategoriesResponseItem>,
) : BaseResponse()
