package com.example.rmaproject2.data.datasource.local

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    open fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    open fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }
}