package com.example.practiselayout

import android.R.layout.simple_spinner_dropdown_item
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var cities: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val cityInput1 = findViewById<AutoCompleteTextView>(R.id.tiInput1)
        val cityInput2 = findViewById<AutoCompleteTextView>(R.id.tiInput2)

        // corutine and adapter to fetch cities from the API
        val cityFetcher=CityFetcher()
        CoroutineScope(Dispatchers.IO).launch {

            val cities = cityFetcher.fetchCities()
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter<String>(
                    this@MainActivity, simple_spinner_dropdown_item, cities
                )
                cityInput1.setAdapter(adapter)
                cityInput2.setAdapter(adapter)
            }
        }


        //the below code  will handle  the onclick event on the submit button
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val city1 = cityInput1.text.toString()
            val city2 = cityInput2.text.toString()
            if (city1.isEmpty()) {
                cityInput1.error = "Please write city name"
                return@setOnClickListener
            }
            if (city2.isEmpty()) {
                cityInput2.error = "Please write city name"
                return@setOnClickListener
            }
            //couroutine for fetching eather data from api and and intent to pass the data to the next activity and also the error handling is also done
            CoroutineScope(Dispatchers.IO).launch {
                val data1 = async { fetchDataFromAPI(city1) }
                val data2 = async { fetchDataFromAPI(city2) }

                val response1 = data1.await();
                val response2 = data2.await();

                response1?.let {
                    response2?.let {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@MainActivity, secondActivity::class.java)

                            intent.apply {
                                putExtra("city1", response1.location.name)
                                putExtra("temperature1", response1.current.temp_c)
                                putExtra("windMph1", response1.current.wind_mph)
                                putExtra("conditionText1", response1.current.condition.text)
                                putExtra("conditionIcon1", response1.current.condition.icon)
                                putExtra("UVIndex1", response1.current.uv)
                                putExtra("humidity1", response1.current.humidity)

                                putExtra("city2", response2.location.name)
                                putExtra("temperature2", response2.current.temp_c)
                                putExtra("windMph2", response2.current.wind_mph)
                                putExtra("conditionText2", response2.current.condition.text)
                                putExtra("conditionIcon2", response2.current.condition.icon)
                                putExtra("UVIndex2", response2.current.uv)
                                putExtra("humidity2", response2.current.humidity)

                                putExtra("showLoadingEffect", true)
                            }

                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    } ?: run {
                        withContext(Dispatchers.Main) {
                            cityInput2.error = "Please write correct city name"
                        }
                    }
                } ?: run {
                    withContext(Dispatchers.Main) {
                        cityInput1.error = "Please write correct city name"
                    }
                }
            }
        }
    }

    // this function is used to fetch  weather data from the API
    private suspend fun fetchDataFromAPI(city: String): WeatherResponse? {
        val retrofit = getRetrofitInstance()

        val service = retrofit.create(responseInterface::class.java)

        return try {
            service.getCurrentWeather("20d77b3776f6403ebdd93905241004", city, "no")
        } catch (e: Exception) {
            null
        }
    }
//end of fetchDataFromAPI
private fun getRetrofitInstance(): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build()
}

}