package com.example.testapp.data.repository

import com.example.testapp.data.local.dao.EventDao
import com.example.testapp.data.mapper.toDomain
import com.example.testapp.data.mapper.toDomainList
import com.example.testapp.data.mapper.toEntityList
import com.example.testapp.data.remote.EventApiService
import com.example.testapp.domain.model.Event
import com.example.testapp.domain.model.Result
import com.example.testapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApiService: EventApiService,
    private val eventDao: EventDao
) : EventRepository {

    override fun getEvents(): Flow<Result<List<Event>>> = eventDao.getEvents()
        .map { entities -> Result.Success(entities.toDomainList()) }
        .catch { e -> Result.Error(e) }

    override fun getEvent(id: Int): Flow<Result<Event>> = eventDao.getEvent(id)
        .map { entity -> Result.Success(entity.toDomain()) }
        .catch { e -> Result.Error(e) }

    override suspend fun refreshEvents() {
        try {
            val events = eventApiService.getEvents()
            eventDao.insertEvents(events.toEntityList())
        } catch (e: Exception) {
            throw e
        }
    }
}