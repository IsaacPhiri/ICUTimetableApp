package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()

        // Check if the user is logged in
        //val currentUser = FirebaseAuth.getInstance().currentUser

        //if (currentUser != null) {
            // User is logged in, proceed to the main timetable page
         //   val intent = Intent(this, AdminDashboardActivity::class.java)
         //   startActivity(intent)
        //} else {
            // User is not logged in, navigate to the login page
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        //}

        // Close the MainActivity so it doesn't stay in the back stack
        finish()

    }
}