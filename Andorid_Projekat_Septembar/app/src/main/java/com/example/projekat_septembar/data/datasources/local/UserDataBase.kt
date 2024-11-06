package com.example.projekat_septembar.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projekat_septembar.data.models.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters
abstract class UserDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

}