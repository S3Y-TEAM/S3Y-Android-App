package com.graduation.domain.models.main.user.create.task.details


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("task")
    val task: Task,
)