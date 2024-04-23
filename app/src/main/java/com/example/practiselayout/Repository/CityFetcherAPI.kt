package com.example.practiselayout.Repository

import com.example.practiselayout.Network.CityServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityFetcherAPI {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.countrystatecity.in/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: CityServiceInterface = retrofit.create(CityServiceInterface::class.java)

    suspend fun fetchCities(): List<String> {
        val cities = service.fetchCities()
        return cities.map { it.name }
    }
}
