package com.isaacphiri2208310810.icutimetableapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AssignedCoursesAdapter(private var course: List<AssignedCourse>) : RecyclerView.Adapter<AssignedCoursesAdapter.AssignedCourseViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newAssignedCourse: List<AssignedCourse>) {
        course = newAssignedCourse
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignedCourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_assigned_courses, parent, false)
        return AssignedCourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssignedCourseViewHolder, position: Int) {
        val course = course[position]
        holder.courseName.text = course.courseName
        holder.programName.text = course.programName
    }

    override fun getItemCount(): Int = course.size

    class AssignedCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.findViewById(R.id.textViewAssignedCourse)
        val programName: TextView = view.findViewById(R.id.textViewProgramAssignedCourse)
    }
}
