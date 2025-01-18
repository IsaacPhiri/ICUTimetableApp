package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        // Initialize API service
        apiService = RetrofitInstance.create(ApiService::class.java)

        // Fetch user profile
        fetchUserProfile()

        val logoutButton: Button = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun fetchUserProfile() {
        val userId = getLoggedInUserId() // Replace with the method to get the logged-in user's ID

        apiService.getUser(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("UserProfileActivity", "Response: $response")
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("UserProfileActivity", "User: $user")
                    if (user != null) {
                        displayUserProfile(user)
                    } else {
                        Toast.makeText(this@UserProfileActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("UserProfileActivity", "Error: ${response.code()}")
                    Toast.makeText(this@UserProfileActivity, "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                    Log.e("UserProfileActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("UserProfileActivity", "Error: ${t.message}")
                Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("UserProfileActivity", "Error: ${t.message}")
            }
        })
    }

    private fun displayUserProfile(user: User) {
        val usernameTextView = findViewById<TextView>(R.id.userName)
        val emailTextView = findViewById<TextView>(R.id.userEmail)
        val phoneTextView = findViewById<TextView>(R.id.phoneNumber)
        val addressTextView = findViewById<TextView>(R.id.userAddress)

        usernameTextView.text = user.username
        emailTextView.text = user.email
        phoneTextView.text = user.phone_number
        addressTextView.text = user.address
    }

    private fun getLoggedInUserId(): String {
        val user = auth.currentUser
        if (user != null) {
            return user.uid
        }
        else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
        return ""
    }
}
