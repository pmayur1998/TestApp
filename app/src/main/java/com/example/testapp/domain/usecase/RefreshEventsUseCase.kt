package com.example.testapp.domain.usecase

import com.example.testapp.domain.repository.EventRepository
import javax.inject.Inject

class RefreshEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke() {
        eventRepository.refreshEvents()
    }
}