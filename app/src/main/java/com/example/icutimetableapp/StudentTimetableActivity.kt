package com.example.icutimetableapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentTimetableActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var timetableAdapter: TimetableAdapter

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_timetable)

        apiService = RetrofitInstance.create(ApiService::class.java)
        timetableAdapter = TimetableAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTimetable)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = timetableAdapter

        fetchTimetable()
    }

    private fun fetchTimetable() {
        apiService.getStudentTimetable().enqueue(object : Callback<List<Timetable>> {
            override fun onResponse(call: Call<List<Timetable>>, response: Response<List<Timetable>>) {
                if (response.isSuccessful) {
                    timetableAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@StudentTimetableActivity, "Failed to fetch sessions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Timetable>>, t: Throwable) {
                Log.e("SchoolTimetableActivity", "Error fetching timetables", t)
                Toast.makeText(this@StudentTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
