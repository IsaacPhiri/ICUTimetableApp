package com.isaacphiri22081310810.icutimetableapp

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

class SchoolTimetableActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var timetableAdapter: TimetableAdapter
    private lateinit var courseIdSpinner: Spinner
    private lateinit var userIdSpinner: Spinner

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_school_timetable)

        apiService = RetrofitInstance.create(ApiService::class.java)
        timetableAdapter = TimetableAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTimetables)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = timetableAdapter

        fetchUsersCoursesTimetables()

        courseIdSpinner = findViewById(R.id.spinner_course_id)
        userIdSpinner = findViewById(R.id.spinner_user_id)

        val daySpinner = findViewById<Spinner>(R.id.spinner_day)
        val timePicker = findViewById<TimePicker>(R.id.time_picker)
        val roomEditText = findViewById<EditText>(R.id.et_room)
        val createTimetableButton = findViewById<Button>(R.id.btn_create_timetable)

        createTimetableButton.setOnClickListener {

            val selectedCourse = courseIdSpinner.selectedItem?.toString()?.toIntOrNull()
            val selectedUser = userIdSpinner.selectedItem?.toString()

            if (selectedCourse == null || selectedUser == null) {
                Toast.makeText(this, "Please select a valid Course ID and User ID", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)

            val timetable = Timetable(
                courseId = selectedCourse,
                day = daySpinner.selectedItem?.toString() ?: "",
                time = time,
                userId = selectedUser,
                room = roomEditText.text.toString(),
                firstName = "",
                lastName = "",
                timetableId = null,
                courseName = ""
            )

            apiService.createTimetable(timetable).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@SchoolTimetableActivity,
                            "Session created successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        fetchUsersCoursesTimetables()
                    } else {
                        Toast.makeText(
                            this@SchoolTimetableActivity,
                            "Failed to create session",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("SchoolTimetableActivity", "Error creating timetable", t)
                    Toast.makeText(
                        this@SchoolTimetableActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun fetchUsersCoursesTimetables() {
        // Fetch users
        apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body() ?: listOf()
                    val userIds = users.map { it.userId }
                    val userAdapter = ArrayAdapter(
                        this@SchoolTimetableActivity,
                        android.R.layout.simple_spinner_item,
                        userIds
                    )
                    userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    userIdSpinner.adapter = userAdapter
                } else {
                    Toast.makeText(
                        this@SchoolTimetableActivity,
                        "Failed to fetch users",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("SchoolTimetableActivity", "Error fetching users", t)
            }
        })

        // Fetch courses
        apiService.getCourses().enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courses = response.body() ?: listOf()
                    val courseIds = courses.map { it.courseId }
                    val courseAdapter = ArrayAdapter(
                        this@SchoolTimetableActivity,
                        android.R.layout.simple_spinner_item,
                        courseIds
                    )
                    courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    courseIdSpinner.adapter = courseAdapter
                } else {
                    Toast.makeText(
                        this@SchoolTimetableActivity,
                        "Failed to fetch courses",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Log.e("SchoolTimetableActivity", "Error fetching courses", t)
            }
        })

        apiService.getAllTimetables().enqueue(object : Callback<List<Timetable>> {
            override fun onResponse(call: Call<List<Timetable>>, response: Response<List<Timetable>>) {
                if (response.isSuccessful) {
                    timetableAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@SchoolTimetableActivity, "Failed to fetch sessions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Timetable>>, t: Throwable) {
                Log.e("SchoolTimetableActivity", "Error fetching timetables", t)
                Toast.makeText(this@SchoolTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
