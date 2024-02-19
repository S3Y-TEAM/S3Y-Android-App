package com.graduation.domain.models.auth.Auth.username

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("userName")
    val userName: String
)