package com.isaacphiri2208310810.icutimetableapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentCoursesActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var studentCoursesAdapter: StudentCoursesAdapter
    private lateinit var auth: FirebaseAuth

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_courses)

        apiService = RetrofitInstance.create(ApiService::class.java)
        studentCoursesAdapter = StudentCoursesAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStudentCourses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentCoursesAdapter

        fetchStudentCourses()
    }

    private fun fetchStudentCourses() {
        val userId = getLoggedInUserId()
        apiService.getStudentCourses(userId).enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    studentCoursesAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@StudentCoursesActivity, "Failed to fetch courses", Toast.LENGTH_SHORT).show()
                    Log.e("SchoolCoursesActivity", "Error fetching courses: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Log.e("SchoolCourseActivity", "Error fetching curses", t)
                Toast.makeText(this@StudentCoursesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getLoggedInUserId(): String {
        val user = auth.currentUser
        if (user != null) {
            return user.uid
        }
        else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            Log.e("StudentCoursesActivity", "User not logged in")
        }
        return ""
    }
}
