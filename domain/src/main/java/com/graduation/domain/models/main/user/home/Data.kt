package com.graduation.domain.models.main.user.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("tasks")
    val tasks: List<Task>,
)