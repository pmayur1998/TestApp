package com.example.testapp.domain.repository

import com.example.testapp.domain.model.Event
import com.example.testapp.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<Result<List<Event>>>
    fun getEvent(id: Int): Flow<Result<Event>>
    suspend fun refreshEvents()
}