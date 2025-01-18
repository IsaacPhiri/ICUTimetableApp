package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StudentDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val timetableButton: Button = findViewById(R.id.timetableButton)
        timetableButton.setOnClickListener {
            startActivity(Intent(this, StudentTimetableActivity::class.java))
        }

        val userProfileButton: Button = findViewById(R.id.accountButton)
        userProfileButton.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
    }
}
