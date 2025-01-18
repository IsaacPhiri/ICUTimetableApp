package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
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
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        // Initialize API service
        apiService = RetrofitInstance.create(ApiService::class.java)

        // Fetch user profile
        fetchUserProfile()

        auth = FirebaseAuth.getInstance()

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
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        displayUserProfile(user)
                    } else {
                        Toast.makeText(this@UserProfileActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UserProfileActivity, "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
        // Replace this with your logic to fetch the logged-in user's ID from Firebase or shared preferences
        return "user-id-placeholder"
    }
}
