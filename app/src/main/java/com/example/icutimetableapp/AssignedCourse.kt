package com.example.icutimetableapp

import com.google.gson.annotations.SerializedName

data class AssignedCourse(
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("program_name")
    val programName: String
)