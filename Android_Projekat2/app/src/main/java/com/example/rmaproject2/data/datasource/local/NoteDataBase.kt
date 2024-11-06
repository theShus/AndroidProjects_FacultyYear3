package com.example.rmaproject2.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.rmaproject2.data.models.note.NoteEntity
import java.util.*

@Database(
    entities = [NoteEntity::class],
    version = 4,
    exportSchema = false
)

@TypeConverters(Converters:: class)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}

