package com.example.testapp.data.mapper

import com.example.testapp.data.local.entity.EventEntity
import com.example.testapp.data.remote.model.EventDto
import com.example.testapp.domain.model.Event

// DTO to Entity
fun EventDto.toEntity(): EventEntity = EventEntity(
    id = id,
    title = title,
    body = body,
    userId = userId
)

// Entity to Domain
fun EventEntity.toDomain(): Event = Event(
    id = id,
    title = title,
    body = body,
    userId = userId
)

// List mappers
fun List<EventDto>.toEntityList(): List<EventEntity> = map { it.toEntity() }
fun List<EventEntity>.toDomainList(): List<Event> = map { it.toDomain() }