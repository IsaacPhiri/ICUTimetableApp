package com.example.icutimetableapp

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val user_id: Int? = null,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("phone_number")
    val phone_number: String? = null,
    @SerializedName("date_of_birth")
    val date_of_birth: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("registration_date")
    val registration_date: String? = null,
    @SerializedName("password")
    val password: String,
)
