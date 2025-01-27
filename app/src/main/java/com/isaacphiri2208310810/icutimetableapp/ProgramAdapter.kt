package com.isaacphiri2208310810.icutimetableapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProgramAdapter(private var programs: List<Program>) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPrograms: List<Program>) {
        programs = newPrograms
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]
        holder.programName.text = program.programName
        holder.programDescription.text = program.description
    }

    override fun getItemCount(): Int = programs.size

    class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val programName: TextView = view.findViewById(R.id.tv_program_name)
        val programDescription: TextView = view.findViewById(R.id.tv_program_description)
    }
}
