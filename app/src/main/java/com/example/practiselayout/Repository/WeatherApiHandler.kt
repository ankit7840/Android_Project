package com.example.practiselayout.Repository

import com.example.practiselayout.Model.WeatherResponse
import com.example.practiselayout.Model.responseInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    class WeatherApiHandler {
        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        suspend fun fetchDataFromAPI(city: String): WeatherResponse? {
            val retrofit = getRetrofitInstance()
            val service = retrofit.create(responseInterface::class.java)

            return try {
                service.getCurrentWeather("20d77b3776f6403ebdd93905241004", city, "no")
            } catch (e: Exception) {
                null
            }
        }
    }


