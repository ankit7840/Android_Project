package com.example.practiselayout.Model

import android.content.Context
import android.content.Intent

class CityData(val city: String, val temperature: Double, val windMph: Double, val conditionText: String, val conditionIcon: String, val uvIndex: Double, val humidity: Int) {
    val displayText: String
        get(): String = " City: $city  \n Temperature:  ${String.format("%.1f C", temperature)}"


    companion object {
        fun fromIntent(intent: Intent, prefix: String): CityData {
            val city = intent.getStringExtra("city$prefix")!!
            val temperature = intent.getDoubleExtra("temperature$prefix", 0.0)
            val windMph = intent.getDoubleExtra("windMph$prefix", 0.0)
            val conditionText = intent.getStringExtra("conditionText$prefix")!!
            val conditionIcon = intent.getStringExtra("conditionIcon$prefix")!!
            val uvIndex = intent.getDoubleExtra("UVIndex$prefix", 0.0)
            val humidity = intent.getIntExtra("humidity$prefix", 0)
            return CityData(city, temperature, windMph, conditionText, conditionIcon, uvIndex, humidity)
        }
    }
    fun toIntent(context: Context, cls: Class<*>): Intent {
        return Intent(context, cls).apply {
            putExtra("city", city)
            putExtra("temperature", temperature)
            putExtra("windMph", windMph)
            putExtra("conditionText", conditionText)
            putExtra("conditionIcon", conditionIcon)
            putExtra("UVIndex", uvIndex)
            putExtra("humidity", humidity)
        }
    }
}
