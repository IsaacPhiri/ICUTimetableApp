package com.isaacphiri2208310810.icutimetableapp

import com.google.gson.annotations.SerializedName

data class Timetable(
    @SerializedName("timetable_id")
    val timetableId: Int? = null,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("room")
    val room: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("courseId")
    val courseId: Int?
)
