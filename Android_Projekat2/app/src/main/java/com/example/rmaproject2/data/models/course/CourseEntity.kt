package com.example.rmaproject2.data.models.course

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val subject: String,
    val type: String,
    val professor: String,
    val groups: String,
    val day: String,
    val time: String,
    val classroom: String
)