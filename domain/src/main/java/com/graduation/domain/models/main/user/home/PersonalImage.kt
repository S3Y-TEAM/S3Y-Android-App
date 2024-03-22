package com.graduation.domain.models.main.user.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PersonalImage(
    @SerializedName("id")
    val id: String,
    @SerializedName("webContentLink")
    val webContentLink: String,
    @SerializedName("webViewLink")
    val webViewLink: String,
)