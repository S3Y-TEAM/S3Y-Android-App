package com.graduation.core.utils.sharedPrefernces

import com.google.gson.annotations.SerializedName

data class LocalUser(
    @SerializedName("Address")
    val address: String? = "",
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("Email")
    val email: String? = "",
    @SerializedName("Fname")
    val fName: String? = "",
    @SerializedName("id")
    val id: Int? = -1,
    @SerializedName("Lname")
    val lName: String? = "",
    @SerializedName("National_id")
    val nationalId: String? = "",
    @SerializedName("Personal_image")
    val personalImage: String? = "",
    @SerializedName("Phone_number")
    val phoneNumber: String? = "",
    @SerializedName("user_name")
    val userName: String? = "",
    @SerializedName("verified")
    val verified: Int? = -1,
)
