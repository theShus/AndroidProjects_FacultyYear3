package com.example.rmaproject2.data.models.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//import java.sql.Date

@Entity(tableName = "notes")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val creationDate: Date,
    val archived : Boolean
)