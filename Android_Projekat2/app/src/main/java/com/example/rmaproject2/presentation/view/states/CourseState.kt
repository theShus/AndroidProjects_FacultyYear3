package com.example.rmaproject2.presentation.view.states

import com.example.rmaproject2.data.models.course.Course

sealed class
CourseState {
    object Loading: CourseState()
    object DataFetched: CourseState()
    data class Success(val courses: List<Course>): CourseState()
    data class Error(val message: String): CourseState()
}