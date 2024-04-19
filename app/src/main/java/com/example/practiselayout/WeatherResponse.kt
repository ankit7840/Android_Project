package com.example.practiselayout

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
    val uv: Double, // Add this line to fetch UV index
    val humidity: Int // Add this line to fetch humidity
)

data class Condition(
    val text: String,
    val icon: String
)
