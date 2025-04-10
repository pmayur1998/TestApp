package com.example.testapp.domain.usecase

import com.example.testapp.domain.model.Event
import com.example.testapp.domain.model.Result
import com.example.testapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(id: Int): Flow<Result<Event>> {
        return eventRepository.getEvent(id)
    }
}