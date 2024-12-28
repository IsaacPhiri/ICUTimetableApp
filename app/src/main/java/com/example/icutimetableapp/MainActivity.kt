package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(R.layout.activity_login)
       // setContentView(R.layout.activity_main)

        //auth = FirebaseAuth.getInstance()

        //val logoutButton: Button = findViewById(R.id.logoutButton)

        //logoutButton.setOnClickListener {
         //   auth.signOut()
        //    startActivity(Intent(this, LoginActivity::class.java))
       //     finish()
       // }
    }
}