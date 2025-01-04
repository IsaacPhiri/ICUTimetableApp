package com.example.icutimetableapp

data class Timetable(
    val id: Int?,
    val courseId: Int,
    val day: String,
    val time: String,
    val room: String
)
