package com.graduation.domain.models.main.user.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Task(
    @SerializedName("Address")
    val address: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("Descr")
    val descr: String,
    @SerializedName("Employer")
    val employer: Employer,
    @SerializedName("Employer_id")
    val employerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img")
    val img: Img,
    @SerializedName("note")
    val note: String,
    @SerializedName("Payments_id")
    val paymentsId: Any,
    @SerializedName("posting_date")
    val postingDate: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_range")
    val priceRange: String,
    @SerializedName("reviews_id")
    val reviewsId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("Title")
    val title: String,
)