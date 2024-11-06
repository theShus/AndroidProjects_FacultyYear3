package com.example.rmaproject2.data.models.note

import java.util.*

//import java.sql.Date

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val creationDate: Date,
    val archived : Boolean
)
