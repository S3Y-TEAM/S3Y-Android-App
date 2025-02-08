package com.graduation.domain.models.main.user.create.task.details


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApplicantsItem(
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
    val note: String,
    @SerializedName("similarProject")
    val similarProject: String,
    @SerializedName("taskId")
    val taskId: Int,
)