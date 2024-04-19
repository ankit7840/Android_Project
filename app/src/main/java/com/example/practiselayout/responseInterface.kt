package com.example.practiselayout
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface responseInterface {
    @GET("current.json")
   suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String
    ): WeatherResponse

}