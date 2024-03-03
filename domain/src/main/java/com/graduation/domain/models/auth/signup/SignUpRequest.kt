package com.graduation.domain.models.auth.signup

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SignUpRequest(
    @SerializedName("Address")
    val address: String? = "",
    @SerializedName("Email")
    val email: String,
    @SerializedName("Fname")
    val fName: String,
    @SerializedName("Links")
    val links: List<Link>? = emptyList(),
    @SerializedName("Lname")
    val lName: String,
    @SerializedName("National_id")
    val nationalId: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Personal_image")
    val personalImage: String? = "",
    @SerializedName("Phone_number")
    val phoneNumber: String,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("certficates")
    val certificates: List<Certificate>? = emptyList(),
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("user_name")
    val userName: String,
)
@Keep
data class Link(
    @SerializedName("Github")
    val github: String,
    @SerializedName("Linkedin")
    val linkedin: String,
)
@Keep
data class Certificate(
    @SerializedName("name")
    val name: String,
)
@Keep
data class Category(
    @SerializedName("category_id")
    val categoryId: Int,
)