package com.example.rmaproject2.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject2.data.models.course.Course
import com.example.rmaproject2.databinding.CourseItemBinding

class CourseViewHolder (private val itemBinding: CourseItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(course: Course){
        itemBinding.subjectTv.text = course.subject
        itemBinding.typeTv.text = course.type
        itemBinding.professorTv.text = course.professor
        itemBinding.classroomTv.text = course.classroom
        itemBinding.groupTv.text = course.group
        itemBinding.dayTv.text = course.day
        itemBinding.timeTv.text = course.time
    }

}