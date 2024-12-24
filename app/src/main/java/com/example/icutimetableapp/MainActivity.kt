package com.example.icutimetableapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        // Sample data for timetable
        val timetableData = listOf(
            TimetableItem("Mathematics", "8:00 AM - 9:00 AM", "Room 101"),
            TimetableItem("Physics", "9:00 AM - 10:00 AM", "Room 202"),
            TimetableItem("Chemistry", "10:30 AM - 11:30 AM", "Room 303")
        )

        // RecyclerView setup
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TimetableAdapter(timetableData)
    }
}