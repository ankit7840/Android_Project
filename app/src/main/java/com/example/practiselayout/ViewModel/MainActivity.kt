package com.example.practiselayout.ViewModel

import android.R.layout.simple_spinner_dropdown_item
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practiselayout.Repository.CityFetcherAPI
import com.example.practiselayout.Model.WeatherResponse
import com.example.practiselayout.R
import com.example.practiselayout.Repository.WeatherApiHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var cityInput1: AutoCompleteTextView
    private lateinit var cityInput2: AutoCompleteTextView
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        cityInput1 = findViewById(R.id.tiInput1)
        cityInput2 = findViewById(R.id.tiInput2)
        btnSubmit = findViewById(R.id.btnSubmit)
        initializeCityInputs()
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
            fetchCityDataAndStartSecondActivity(city1, city2)
        }
    }

    private fun initializeCityInputs() {
        val cityFetcherAPI = CityFetcherAPI()
        CoroutineScope(Dispatchers.IO).launch {
            val cities = cityFetcherAPI.fetchCities()
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter<String>(
                    this@MainActivity, simple_spinner_dropdown_item, cities
                )
                cityInput1.setAdapter(adapter)
                cityInput2.setAdapter(adapter)
            }
        }
    }

    private val weatherApiHandler = WeatherApiHandler();
    private fun fetchCityDataAndStartSecondActivity(city1: String, city2: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val data1 = async { weatherApiHandler.fetchDataFromAPI(city1) }
            val data2 = async { weatherApiHandler.fetchDataFromAPI(city2) }

            val response1 = data1.await()
            val response2 = data2.await()

            response1?.let {
                response2?.let {
                    withContext(Dispatchers.Main) {
                        startSecondActivity(response1, response2)
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
    private fun startSecondActivity(response1: WeatherResponse, response2: WeatherResponse) {
        val intent = Intent(this@MainActivity, secondActivity::class.java)
        intent.putWeatherData("1", response1)
        intent.putWeatherData("2", response2)
        startActivity(intent)
    }
    private fun Intent.putWeatherData(prefix: String, response: WeatherResponse) {
        putExtra("city$prefix", response.location.name)
        putExtra("temperature$prefix", response.current.temp_c)
        putExtra("windMph$prefix", response.current.wind_mph)
        putExtra("conditionText$prefix", response.current.condition.text)
        putExtra("conditionIcon$prefix", response.current.condition.icon)
        putExtra("UVIndex$prefix", response.current.uv)
        putExtra("humidity$prefix", response.current.humidity)
        putExtra("showLoadingEffect", true)
    }

}