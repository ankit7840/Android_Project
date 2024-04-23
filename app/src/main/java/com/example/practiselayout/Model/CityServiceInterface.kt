package com.example.practiselayout.Network

import com.example.practiselayout.Model.CityDataClass
import retrofit2.http.GET
import retrofit2.http.Headers

interface CityServiceInterface{
    @Headers("X-CSCAPI-KEY: NWpTNWVYZnV3MzQzQmxWM0tZUkw5SmZUT1phQ1cxTGN5Rm1GWTcydA==")
    @GET("countries/IN/cities")
    suspend fun fetchCities(): List<CityDataClass>
}