package com.example.rmaproject2.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rmaproject2.data.models.course.Course

class CourseDiffCallback : DiffUtil.ItemCallback<Course>(){

    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return  oldItem.subject == newItem.subject &&
                oldItem.type == newItem.type &&
                oldItem.professor == newItem.professor &&
                oldItem.classroom == newItem.classroom &&
                oldItem.group == newItem.group &&
                oldItem.day == newItem.day &&
                oldItem.time == newItem.time
    }
}