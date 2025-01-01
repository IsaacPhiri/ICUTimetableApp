package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val accountButton: Button = findViewById(R.id.accountButton)
        accountButton.setOnClickListener {
            startActivity(Intent(this, UserAccountsActivity::class.java))
        }

        val courseButton: Button = findViewById(R.id.courseButton)
        courseButton.setOnClickListener {
            startActivity(Intent(this, ProgramsActivity::class.java))
        }

        val timetableButton: Button = findViewById(R.id.timetableButton)
        timetableButton.setOnClickListener {
            startActivity(Intent(this, SchoolTimetableActivity::class.java))
        }

        val createCoursesButton: Button = findViewById(R.id.createCourses)
        createCoursesButton.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }

        val enrollmentButton: Button = findViewById(R.id.enrollmentButton)
        enrollmentButton.setOnClickListener {
            startActivity(Intent(this, EnrollmentsActivity::class.java))
        }

        val assignCourses: Button = findViewById(R.id.assignCourses)
        assignCourses.setOnClickListener {
            startActivity(Intent(this, CourseAssignmentActivity::class.java))
        }
    }
}
