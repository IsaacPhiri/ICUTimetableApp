package com.example.icutimetableapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.loginEmail)
        val passwordEditText: EditText = findViewById(R.id.loginPassword)
        val loginButton: Button = findViewById(R.id.loginButton)

        // Handle Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            Firebase.database.reference.child("users").child(userId!!)
                                .get()
                                .addOnSuccessListener { snapshot ->
                                    val role = snapshot.child("role").value.toString()
                                    when (role) {
                                        "student" -> startActivity(Intent(this, StudentDashboardActivity::class.java))
                                        "lecturer" -> startActivity(Intent(this, LecturerDashboardActivity::class.java))
                                        "admin" -> startActivity(Intent(this, AdminDashboardActivity::class.java))
                                        else -> Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show()
                                    }
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed to retrieve user role", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
