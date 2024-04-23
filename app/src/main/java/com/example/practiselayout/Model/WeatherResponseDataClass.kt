package com.example.practiselayout.Model

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    val temp_c: Double,
    val wind_mph: Double,
    val condition: Condition,
    val uv: Double,
    val humidity: Int
)

data class Condition(
    val text: String,
    val icon: String
)
