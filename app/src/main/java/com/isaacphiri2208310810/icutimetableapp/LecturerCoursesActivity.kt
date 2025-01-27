package com.isaacphiri22081310810.icutimetableapp

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

class LecturerCoursesActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var assignedCoursesAdapter: AssignedCoursesAdapter
    private lateinit var auth: FirebaseAuth

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lecturer_courses)

        apiService = RetrofitInstance.create(ApiService::class.java)
        assignedCoursesAdapter = AssignedCoursesAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAssignedCourses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = assignedCoursesAdapter

        fetchStudentCourses()
    }

    private fun fetchStudentCourses() {
        val userId = getLoggedInUserId()
        apiService.getAssignedCourses(userId).enqueue(object : Callback<List<AssignedCourse>> {
            override fun onResponse(call: Call<List<AssignedCourse>>, response: Response<List<AssignedCourse>>) {
                if (response.isSuccessful) {
                    assignedCoursesAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@LecturerCoursesActivity, "Failed to fetch courses", Toast.LENGTH_SHORT).show()
                    Log.e("LecturerCoursesActivity", "Error fetching courses: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AssignedCourse>>, t: Throwable) {
                Log.e("LecturerCourseActivity", "Error fetching courses", t)
                Toast.makeText(this@LecturerCoursesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
            Log.e("LecturerCoursesActivity", "User not logged in")
        }
        return ""
    }
}
