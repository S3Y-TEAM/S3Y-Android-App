package com.graduation.domain.models.main.dev.apply


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("newApplication")
    val newApplication: NewApplication,
)