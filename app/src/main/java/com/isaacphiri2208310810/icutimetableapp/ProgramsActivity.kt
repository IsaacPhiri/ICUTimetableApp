package com.isaacphiri22081310810.icutimetableapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramsActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var programAdapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_programs)

        apiService = RetrofitInstance.create(ApiService::class.java)
        programAdapter = ProgramAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPrograms)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = programAdapter

        fetchPrograms()

        val programName = findViewById<EditText>(R.id.et_program_name)
        val programDescription = findViewById<EditText>(R.id.et_program_description)
        val createProgramButton = findViewById<Button>(R.id.btn_create_program)

        createProgramButton.setOnClickListener {
            val program = Program (
                programName = programName.text.toString(),
                description = programDescription.text.toString()
            )
            Log.d("ProgramsActivity", "Name: $programName, Description: $programDescription")
            apiService.addProgram(program).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("ProgramsActivity", "Name: $programName, Description: $programDescription")
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProgramsActivity, "Program created successfully!", Toast.LENGTH_SHORT).show()
                        fetchPrograms()
                    } else {
                        Toast.makeText(this@ProgramsActivity, "Failed to create program", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ProgramsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fetchPrograms() {
        apiService.getPrograms().enqueue(object : Callback<List<Program>> {
            override fun onResponse(call: Call<List<Program>>, response: Response<List<Program>>) {
                if (response.isSuccessful) {
                    programAdapter.updateData(response.body() ?: listOf())
                } else {
                    Toast.makeText(this@ProgramsActivity, "Failed to fetch programs", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Program>>, t: Throwable) {
                Toast.makeText(this@ProgramsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
