package com.isaacphiri2208310810.icutimetableapp

import com.google.gson.annotations.SerializedName

data class AssignedCourse(
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("program_name")
    val programName: String
)