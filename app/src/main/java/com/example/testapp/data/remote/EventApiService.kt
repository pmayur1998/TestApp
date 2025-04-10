package com.example.testapp.data.remote

import com.example.testapp.data.remote.model.EventDto
import retrofit2.http.GET

interface EventApiService {
    @GET("posts")
    suspend fun getEvents(): List<EventDto>
}