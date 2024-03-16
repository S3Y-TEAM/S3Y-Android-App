package com.graduation.domain.models.main.dev.applied


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("applications")
    val applications: List<Application>,
)