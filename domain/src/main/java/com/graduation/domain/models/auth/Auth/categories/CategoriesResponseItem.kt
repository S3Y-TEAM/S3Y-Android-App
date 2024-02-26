package com.graduation.domain.models.auth.Auth.categories

import com.google.gson.annotations.SerializedName

data class CategoriesResponseItem(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent")
    val parent: String,
)