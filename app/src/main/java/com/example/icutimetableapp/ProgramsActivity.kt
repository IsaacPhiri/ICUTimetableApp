package com.example.icutimetableapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramsActivity : AppCompatActivity() {

    private lateinit var etProgramId: EditText
    private lateinit var etProgramName: EditText
    private lateinit var etProgramDescription: EditText
    private lateinit var btnCreateProgram: Button
    private lateinit var btnUpdateProgram: Button
    private lateinit var btnDeleteProgram: Button
    private lateinit var lvPrograms: ListView

    private lateinit var programList: MutableList<Program>
    private lateinit var selectedProgram: Program

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programs)

        apiService = RetrofitInstance.create(ApiService::class.java)

        // Initialize Views
        etProgramId = findViewById(R.id.et_program_id)
        etProgramName = findViewById(R.id.et_program_name)
        etProgramDescription = findViewById(R.id.et_program_description)
        btnCreateProgram = findViewById(R.id.btn_create_program)
        btnUpdateProgram = findViewById(R.id.btn_update_program)
        btnDeleteProgram = findViewById(R.id.btn_delete_program)
        lvPrograms = findViewById(R.id.lv_programs)

        programList = mutableListOf()

        // Load Programs
        loadPrograms()

        // Set Listeners
        lvPrograms.setOnItemClickListener { _, _, position, _ ->
            selectedProgram = programList[position]
            etProgramId.setText(selectedProgram.programId.toString())
            etProgramName.setText(selectedProgram.programName)
            etProgramDescription.setText(selectedProgram.description)
        }

        btnCreateProgram.setOnClickListener { createProgram() }
        btnUpdateProgram.setOnClickListener { updateProgram() }
        btnDeleteProgram.setOnClickListener { deleteProgram() }
    }

    private fun loadPrograms() {
        apiService.getPrograms().enqueue(object : Callback<List<Program>> {
            override fun onResponse(call: Call<List<Program>>, response: Response<List<Program>>) {
                if (response.isSuccessful) {
                    programList.clear()
                    response.body()?.let { programList.addAll(it) }
                    updateProgramListView()
                } else {
                    showToast("Failed to load programs")
                }
            }

            override fun onFailure(call: Call<List<Program>>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun createProgram() {
        val name = etProgramName.text.toString()
        val description = etProgramDescription.text.toString()

        if (name.isEmpty() || description.isEmpty()) {
            showToast("All fields are required")
            return
        }

        val programRequest = ProgramRequest(name, description)
        apiService.addProgram(programRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Program created")
                    loadPrograms()
                } else {
                    showToast("Failed to create program")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun updateProgram() {
        val id = etProgramId.text.toString().toIntOrNull()
        val name = etProgramName.text.toString()
        val description = etProgramDescription.text.toString()

        if (id == null || name.isEmpty() || description.isEmpty()) {
            showToast("All fields are required")
            return
        }

        val programRequest = ProgramRequest(name, description)
        apiService.updateProgram(id, programRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Program updated")
                    loadPrograms()
                } else {
                    showToast("Failed to update program")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun deleteProgram() {
        val id = etProgramId.text.toString().toIntOrNull()

        if (id == null) {
            showToast("Select a program to delete")
            return
        }

        apiService.deleteProgram(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Program deleted")
                    loadPrograms()
                } else {
                    showToast("Failed to delete program")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun updateProgramListView() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            programList.map { it.programName }
        )
        lvPrograms.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
