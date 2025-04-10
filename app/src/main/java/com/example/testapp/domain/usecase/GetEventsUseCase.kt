package com.example.testapp.domain.usecase

import com.example.testapp.domain.model.Event
import com.example.testapp.domain.model.Result
import com.example.testapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(): Flow<Result<List<Event>>> {
        return eventRepository.getEvents()
    }
}