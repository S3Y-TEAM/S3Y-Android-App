package com.graduation.domain.models.auth.categories

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CategoriesResponseItem(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent")
    val parent: String,
)