package com.graduation.domain.models.main.user.create.task


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NewTask(
    @SerializedName("Address")
    val address: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("city")
    val city: Any,
    @SerializedName("country")
    val country: Any,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("Descr")
    val descr: String,
    @SerializedName("Employer_id")
    val employerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img")
    val img: Img,
    @SerializedName("note")
    val note: Any,
    @SerializedName("Payments_id")
    val paymentsId: Any,
    @SerializedName("posting_date")
    val postingDate: String,
    @SerializedName("price")
    val price: Any,
    @SerializedName("price_range")
    val priceRange: String,
    @SerializedName("reviews_id")
    val reviewsId: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("Title")
    val title: String,
)