package com.example.icutimetableapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(private var courses: List<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCourses: List<Course>) {
        courses = newCourses
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.courseName.text = course.courseName
        holder.programName.text = course.programName
    }

    override fun getItemCount(): Int = courses.size

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.findViewById(R.id.textViewCoursename)
        val programName: TextView = view.findViewById(R.id.textViewProgramName)
    }
}
