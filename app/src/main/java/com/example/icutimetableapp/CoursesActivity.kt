package com.example.icutimetableapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : AppCompatActivity() {

    private lateinit var courseNameInput: EditText
    private lateinit var programSpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var coursesList: ListView

    private var selectedCourseId: Int? = null
    private val apiService = RetrofitInstance.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        // Initialize Views
        courseNameInput = findViewById(R.id.input_course_name)
        programSpinner = findViewById(R.id.spinner_program_id)
        addButton = findViewById(R.id.btn_add_course)
        updateButton = findViewById(R.id.btn_update_course)
        deleteButton = findViewById(R.id.btn_delete_course)
        coursesList = findViewById(R.id.list_courses)

        // Load Programs into Spinner
        loadPrograms()

        // Load Courses List
        loadCourses()

        // Add Course
        addButton.setOnClickListener {
            val courseName = courseNameInput.text.toString()
            val programId = (programSpinner.selectedItem as Program).programId
            if (courseName.isNotBlank()) {
                if (programId != null) {
                    addCourse(courseName, programId)
                }
            } else {
                Toast.makeText(this, "Enter course name", Toast.LENGTH_SHORT).show()
            }
        }

        // Update Course
        updateButton.setOnClickListener {
            val courseName = courseNameInput.text.toString()
            val programId = (programSpinner.selectedItem as Program).programId
            selectedCourseId?.let { id ->
                if (programId != null) {
                    updateCourse(id, courseName, programId)
                }
            } ?: Toast.makeText(this, "Select a course to update", Toast.LENGTH_SHORT).show()
        }

        // Delete Course
        deleteButton.setOnClickListener {
            selectedCourseId?.let { id ->
                deleteCourse(id)
            } ?: Toast.makeText(this, "Select a course to delete", Toast.LENGTH_SHORT).show()
        }

        // Handle Course Selection
        coursesList.setOnItemClickListener { _, _, position, _ ->
            val selectedCourse = coursesList.adapter.getItem(position) as Course
            selectedCourseId = selectedCourse.courseId
            courseNameInput.setText(selectedCourse.courseName)
            programSpinner.setSelection(getSpinnerIndex(programSpinner, selectedCourse.programId))
        }
    }

    // Load Programs
    private fun loadPrograms() {
        apiService.getPrograms().enqueue(object : Callback<List<Program>> {
            override fun onResponse(call: Call<List<Program>>, response: Response<List<Program>>) {
                if (response.isSuccessful) {
                    val programs = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@CoursesActivity, android.R.layout.simple_spinner_item, programs)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    programSpinner.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Program>>, t: Throwable) {
                Toast.makeText(this@CoursesActivity, "Failed to load programs", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Load Courses
    private fun loadCourses() {
        apiService.getCourses().enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courses = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@CoursesActivity, android.R.layout.simple_list_item_1, courses)
                    coursesList.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Toast.makeText(this@CoursesActivity, "Failed to load courses", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Add Course
    private fun addCourse(courseName: String, programId: Int) {
        apiService.addCourse(CourseRequest(courseName, programId)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CoursesActivity, "Course added successfully", Toast.LENGTH_SHORT).show()
                    loadCourses()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CoursesActivity, "Failed to add course", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Update Course
    private fun updateCourse(courseId: Int, courseName: String, programId: Int) {
        apiService.updateCourse(courseId, CourseRequest(courseName, programId)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CoursesActivity, "Course updated successfully", Toast.LENGTH_SHORT).show()
                    loadCourses()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CoursesActivity, "Failed to update course", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Delete Course
    private fun deleteCourse(courseId: Int) {
        apiService.deleteCourse(courseId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CoursesActivity, "Course deleted successfully", Toast.LENGTH_SHORT).show()
                    loadCourses()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CoursesActivity, "Failed to delete course", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Helper to Get Spinner Index
    private fun getSpinnerIndex(spinner: Spinner, programId: Int): Int {
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as Program).programId == programId) return i
        }
        return 0
    }
}
