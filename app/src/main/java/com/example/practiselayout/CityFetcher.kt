package com.example.practiselayout

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CityFetcher {
    suspend fun fetchCities(): List<String> {
        val url = URL("https://api.countrystatecity.in/v1/countries/IN/cities")
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty(
            "X-CSCAPI-KEY", "NWpTNWVYZnV3MzQzQmxWM0tZUkw5SmZUT1phQ1cxTGN5Rm1GWTcydA=="
        )

        val responseCode = connection.responseCode
        val cities = mutableListOf<String>()
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()
            withContext(Dispatchers.IO) {
                reader.close()
            }

            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val city = jsonArray.getJSONObject(i).getString("name")
                cities.add(city)
            }
        }

        connection.disconnect()
        return cities
    }


}
