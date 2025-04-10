package com.example.testapp.data.remote.model

import com.squareup.moshi.Json

data class EventDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String,
    @Json(name = "userId") val userId: Int
)