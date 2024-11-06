package com.example.rmaproject2.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rmaproject2.data.models.course.Course
import com.example.rmaproject2.databinding.CourseItemBinding
import com.example.rmaproject2.presentation.view.recycler.diff.CourseDiffCallback
import com.example.rmaproject2.presentation.view.recycler.viewHolder.CourseViewHolder

class CourseAdapter : ListAdapter<Course, CourseViewHolder>(CourseDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemBinding = CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}