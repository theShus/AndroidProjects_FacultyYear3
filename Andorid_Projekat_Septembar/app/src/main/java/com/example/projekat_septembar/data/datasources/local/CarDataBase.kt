package com.example.projekat_septembar.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projekat_septembar.data.models.CarEntity


@Database(
    entities = [CarEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters
abstract class CarDataBase : RoomDatabase() {
    abstract fun getCarDao(): CarDao
}
