package com.isaacphiri2208310810.icutimetableapp

import com.google.gson.annotations.SerializedName

data class Program (
    @SerializedName("program_id")
    val programId: Int? = null,
    @SerializedName("program_name")
    val programName: String,
    @SerializedName("description")
    val description: String
)
