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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.JsonElement

class StudentTimetableActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var timetableAdapter: TimetableAdapter
    private lateinit var auth: FirebaseAuth

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

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
        val userId = getLoggedInUserId()
            ?: // Don't proceed if the user is not logged in
            return

        apiService.getStudentTimetable(userId).enqueue(object : Callback<ResponseBody> { // Change the return type to ResponseBody
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseString = response.body()?.string() ?: ""

                    // Parse the response using Gson
                    val gson = Gson()
                    val element = gson.fromJson(responseString, JsonElement::class.java)

                    when {
                        element.isJsonObject -> {
                            val obj = element.asJsonObject
                            // Handle the object response
                            val timetableArray = obj.getAsJsonArray("data") // Replace with the actual key in your response
                            val timetableList = gson.fromJson(timetableArray, Array<Timetable>::class.java).toList()
                            timetableAdapter.updateData(timetableList)
                        }
                        element.isJsonArray -> {
                            val array = element.asJsonArray
                            // Handle the array response directly
                            val timetableList = gson.fromJson(array, Array<Timetable>::class.java).toList()
                            timetableAdapter.updateData(timetableList)
                        }
                        else -> {
                            Log.e("StudentTimetableActivity", "Unexpected response format")
                            Toast.makeText(this@StudentTimetableActivity, "Unexpected response format", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@StudentTimetableActivity, "Failed to fetch sessions", Toast.LENGTH_SHORT).show()
                    Log.e("StudentTimetableActivity", "Error fetching timetables: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("StudentTimetableActivity", "Network call failed: ${t.message}")
                Toast.makeText(this@StudentTimetableActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getLoggedInUserId(): String? {
        val user = auth.currentUser
        if (user != null) {
            return user.uid
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            Log.e("StudentTimetableActivity", "User not logged in")
            return null
        }
    }
}
