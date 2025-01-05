package com.example.icutimetableapp

import com.google.gson.annotations.SerializedName

data class Program (
    @SerializedName("program_id")
    val programId: Int? = null,
    @SerializedName("program_name")
    val programName: String,
    @SerializedName("description")
    val description: String
)
