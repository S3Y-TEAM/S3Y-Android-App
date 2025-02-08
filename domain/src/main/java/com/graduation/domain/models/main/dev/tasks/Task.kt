package com.graduation.domain.models.main.dev.tasks


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Task(
    @SerializedName("Address")
    val address: String,
    @SerializedName("category")
    val category: Category,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("Descr")
    val descr: String,
    @SerializedName("Employer_id")
    val employerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img")
    val img: Any,
    @SerializedName("note")
    val note: Any,
    @SerializedName("Payments_id")
    val paymentsId: Any,
    @SerializedName("posting_date")
    val postingDate: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("reviews_id")
    val reviewsId: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("Title")
    val title: String,
)