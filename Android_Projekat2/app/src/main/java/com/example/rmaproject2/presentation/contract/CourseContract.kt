package com.example.rmaproject2.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rmaproject2.presentation.view.states.CourseState

interface CourseContract {
    interface ViewModel {
        val courseState: LiveData<CourseState>

        fun fetchAllCourses()
        fun getAllCourses()
        fun getByFilter(subjectOrProfessor: String, group: String, day: String)
    }
}