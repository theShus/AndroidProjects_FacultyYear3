package com.example.rmaproject2.data.models.course

data class Course(
    val id: Long,
    val subject: String,
    val type: String,
    val professor: String,
    val classroom: String,
    val group: String,
    val day: String,
    val time: String
)
