package com.example.testapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)