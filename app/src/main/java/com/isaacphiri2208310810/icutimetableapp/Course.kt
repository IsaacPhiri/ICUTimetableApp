package com.isaacphiri2208310810.icutimetableapp

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("course_id")
    val courseId: Int?,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("program_id")
    val programId: Int?,
    @SerializedName("program_name")
    val programName: String
)