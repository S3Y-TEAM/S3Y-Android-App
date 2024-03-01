package com.graduation.domain.models.auth.Auth.username

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserNameRequest(
    @SerializedName("userName")
    val userName : String
)