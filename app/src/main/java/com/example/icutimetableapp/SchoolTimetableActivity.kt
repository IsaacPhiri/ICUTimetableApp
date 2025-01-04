package com.example.icutimetableapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SchoolTimetableActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var etTimetableId: EditText
    private lateinit var etCourseId: EditText
    private lateinit var spinnerDay: Spinner
    private lateinit var timePicker: TimePicker
    private lateinit var etRoom: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var lvTimetable: ListView
    private lateinit var calendarView: CalendarView
    private lateinit var tvSelectedDate: TextView
    private lateinit var lvTimetableDetails: ListView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_timetable)

        // Initialize views
        etTimetableId = findViewById(R.id.et_timetable_id)
        etCourseId = findViewById(R.id.et_course_id)
        spinnerDay = findViewById(R.id.spinner_day)
        timePicker = findViewById(R.id.time_picker)
        etRoom = findViewById(R.id.et_room)
        btnCreate = findViewById(R.id.btn_create_timetable)
        btnUpdate = findViewById(R.id.btn_update_timetable)
        btnDelete = findViewById(R.id.btn_delete_timetable)
        lvTimetable = findViewById(R.id.lv_timetable)
        calendarView = findViewById(R.id.calendar_view)
        tvSelectedDate = findViewById(R.id.tv_selected_date)
        lvTimetableDetails = findViewById(R.id.lv_timetable_details)

        // Add listeners for buttons
        btnCreate.setOnClickListener { createTimetable() }
        btnUpdate.setOnClickListener { updateTimetable() }
        btnDelete.setOnClickListener { deleteTimetable() }

        // Set CalendarView date change listener
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            tvSelectedDate.text = "Selected Date: $selectedDate"
            fetchTimetableByDate(selectedDate)
        }

        // Fetch initial timetable
        fetchTimetable()
    }

    private fun createTimetable() {
        val courseId = etCourseId.text.toString().toInt()
        val day = spinnerDay.selectedItem.toString()
        val time = "${timePicker.hour}:${timePicker.minute}"
        val room = etRoom.text.toString()

        val timetable = Timetable(null, courseId, day, time, room)

        if (timetable.day.isEmpty() || timetable.time.isEmpty() || timetable.room.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        apiService = RetrofitInstance.create(ApiService::class.java)
        apiService.createTimetable(timetable).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SchoolTimetableActivity, "Timetable created successfully", Toast.LENGTH_SHORT).show()
                    fetchTimetable()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchTimetable() {
        val apiService = RetrofitInstance.create(ApiService::class.java)
        apiService.getAllTimetables().enqueue(object : Callback<List<Timetable>> {
            override fun onResponse(call: Call<List<Timetable>>, response: Response<List<Timetable>>) {
                if (response.isSuccessful) {
                    val timetableList = response.body()!!
                    val adapter = ArrayAdapter(
                        this@SchoolTimetableActivity,
                        android.R.layout.list_content,
                        timetableList.map { "${it.day}, ${it.time} - ${it.room}" }
                    )
                    lvTimetable.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Timetable>>, t: Throwable) {
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTimetable() {
        val id = etTimetableId.text.toString().toInt()
        val courseId = etCourseId.text.toString().toInt()
        val day = spinnerDay.selectedItem.toString()
        val time = "${timePicker.hour}:${timePicker.minute}"
        val room = etRoom.text.toString()

        val timetable = Timetable(id, courseId, day, time, room)

        val apiService = RetrofitInstance.create(ApiService::class.java)
        apiService.updateTimetable(id, timetable).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SchoolTimetableActivity, "Timetable updated successfully", Toast.LENGTH_SHORT).show()
                    fetchTimetable()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteTimetable() {
        val id = etTimetableId.text.toString().toInt()

        val apiService = RetrofitInstance.create(ApiService::class.java)
        apiService.deleteTimetable(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SchoolTimetableActivity, "Timetable deleted successfully", Toast.LENGTH_SHORT).show()
                    fetchTimetable()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun fetchTimetableByDate(date: String) {
        val apiService = RetrofitInstance.create(ApiService::class.java)
        apiService.getTimetableByDate(date).enqueue(object : Callback<List<Timetable>> {
            override fun onResponse(call: Call<List<Timetable>>, response: Response<List<Timetable>>) {
                if (response.isSuccessful) {
                    val timetableList = response.body()!!
                    val adapter = ArrayAdapter(
                        this@SchoolTimetableActivity,
                        android.R.layout.simple_list_item_multiple_choice,
                        timetableList.map { "${it.time} - ${it.room}" }
                    )
                    lvTimetableDetails.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Timetable>>, t: Throwable) {
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
