package com.graduation.domain.models.main.dev.applied


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Applicant(
    @SerializedName("accepted")
    val accepted: Boolean,
    @SerializedName("coverLetter")
    val coverLetter: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("employeeId")
    val employeeId: Int,
    @SerializedName("expectedBudget")
    val expectedBudget: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("note")
    val note: Any,
    @SerializedName("similarProject")
    val similarProject: String,
    @SerializedName("taskId")
    val taskId: Int,
)