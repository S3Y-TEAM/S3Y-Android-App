package com.graduation.domain.models.auth.username

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserNameRequest(
    @SerializedName("userName")
    val username : String
)