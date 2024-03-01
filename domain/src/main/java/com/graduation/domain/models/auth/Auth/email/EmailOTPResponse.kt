package com.graduation.domain.models.auth.Auth.email

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.graduation.core.base.network.BaseResponse
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponseItem

@Keep

data class EmailOTPResponse(
    @SerializedName("data")
    val data: EmailOTPResponseItem,
) : BaseResponse()