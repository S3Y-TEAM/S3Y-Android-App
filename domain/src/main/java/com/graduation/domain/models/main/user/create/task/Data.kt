package com.graduation.domain.models.main.user.create.task


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("newTask")
    val newTask: NewTask,
)