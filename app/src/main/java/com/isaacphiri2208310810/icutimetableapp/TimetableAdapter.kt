package com.isaacphiri22081310810.icutimetableapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimetableAdapter(private var timetables: List<Timetable>) : RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTimetables: List<Timetable>) {
        timetables = newTimetables
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timetable, parent, false)
        return TimetableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        val timetable = timetables[position]
        holder.courseName.text = timetable.courseName
        holder.day.text = timetable.day
        holder.time.text = timetable.time
        holder.room.text = timetable.room
        holder.firstName.text = timetable.firstName
        holder.lastName.text = timetable.lastName
    }

    override fun getItemCount(): Int = timetables.size

    class TimetableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.findViewById(R.id.textViewCoursename)
        val day: TextView = view.findViewById(R.id.textViewDay)
        val time: TextView = view.findViewById(R.id.textViewTime)
        val room: TextView = view.findViewById(R.id.textViewRoom)
        val firstName: TextView = view.findViewById(R.id.textViewFirstname)
        val lastName: TextView = view.findViewById(R.id.textViewLastname)
    }
}
