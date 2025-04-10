package com.example.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.local.dao.EventDao
import com.example.testapp.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}