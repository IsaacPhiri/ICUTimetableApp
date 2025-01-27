package com.isaacphiri2208310810.icutimetableapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LecturerDashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_dashboard)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val lecturerAccountButton: Button = findViewById(R.id.lecturerAccountButton)
        lecturerAccountButton.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }

        val timetableButton: Button = findViewById(R.id.lecturerTimetableButton)
        timetableButton.setOnClickListener {
            startActivity(Intent(this, StudentTimetableActivity::class.java))
        }

        val lecturerCoursesButton: Button = findViewById(R.id.lecturerCoursesButton)
        lecturerCoursesButton.setOnClickListener {
            startActivity(Intent(this, LecturerCoursesActivity::class.java))
        }
    }
}
