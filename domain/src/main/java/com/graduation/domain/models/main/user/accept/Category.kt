package com.graduation.domain.models.main.user.accept


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent")
    val parent: String,
)