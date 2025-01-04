package com.example.icutimetableapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.EditText
import android.widget.Spinner

class UserAccountsActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_accounts)

        apiService = RetrofitInstance.create(ApiService::class.java)
        userAdapter = UserAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        fetchUsers()

        val username = findViewById<EditText>(R.id.et_username)
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        val firstName = findViewById<EditText>(R.id.et_first_name)
        val lastName = findViewById<EditText>(R.id.et_last_name)
        val role = findViewById<Spinner>(R.id.sp_role)
        val phoneNumber = findViewById<EditText>(R.id.et_phone_number)
        val dateOfBirth = findViewById<EditText>(R.id.et_dob)
        val address = findViewById<EditText>(R.id.et_address)
        val createUserButton = findViewById<Button>(R.id.btn_save_user)

        createUserButton.setOnClickListener {
            val user = User(
                username = username.text.toString(),
                email = email.text.toString(),
                password = password.text.toString(),
                first_name = firstName.text.toString(),
                last_name = lastName.text.toString(),
                role = role.selectedItem.toString(),
                phone_number = phoneNumber.text.toString(),
                date_of_birth = dateOfBirth.text.toString(),
                address = address.text.toString(),
            )

            apiService.createUser(user).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UserAccountsActivity, "User created successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity
                    } else {
                        Toast.makeText(this@UserAccountsActivity, "Failed to create user", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@UserAccountsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fetchUsers() {
        apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    userAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@UserAccountsActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@UserAccountsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}