package com.example.rmaproject2.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rmaproject2.data.models.course.CourseEntity

@Database(
    entities = [CourseEntity::class],
    version = 4,
    exportSchema = false)
@TypeConverters()
abstract class CourseDataBase : RoomDatabase(){
    abstract fun getCourseDao(): CourseDao
}