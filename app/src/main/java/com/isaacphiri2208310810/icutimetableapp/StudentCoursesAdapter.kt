package com.isaacphiri2208310810.icutimetableapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentCoursesAdapter(private var courses: List<Course>) : RecyclerView.Adapter<StudentCoursesAdapter.StudentCoursesViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCourses: List<Course>) {
        courses = newCourses
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentCoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student_courses, parent, false)
        return StudentCoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentCoursesViewHolder, position: Int) {
        val course = courses[position]
        holder.courseName.text = course.courseName
    }

    override fun getItemCount(): Int = courses.size

    class StudentCoursesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.findViewById(R.id.textViewCoursename)
    }
}
