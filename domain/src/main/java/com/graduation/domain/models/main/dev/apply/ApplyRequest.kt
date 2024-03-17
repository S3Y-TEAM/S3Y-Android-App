package com.graduation.domain.models.main.dev.apply


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApplyRequest(
    @SerializedName("coverLetter")
    val coverLetter: String,
    @SerializedName("deadline")
    val deadline: String,
//    @SerializedName("employeeId")
//    val employeeId: Int,
    @SerializedName("expectedBudget")
    val expectedBudget: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("similarProject")
    val similarProject: String,
)