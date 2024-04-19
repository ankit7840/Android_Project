package com.example.practiselayout

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details) // Replace with your layout resource

        setContentView(R.layout.details) // Replace with your layout resource

        // Retrieve additional details from the Intent
        val windMph = intent.getDoubleExtra("windMph", 0.11) //correct
        val conditionText = intent.getStringExtra("conditionText") //correct
        val conditionIcon = intent.getStringExtra("conditionIcon")//correct
        val temperature = intent.getDoubleExtra("temperature", 0.0)//correct
        val city = intent.getStringExtra("city")//correct
        val UvIndex = intent.getDoubleExtra("UVIndex", 0.0);//correct
        val humidity = intent.getIntExtra("humidity", 0);//correct
        val Aqi1 = intent.getDoubleExtra("Aqi_Index", 0.0);

        // now mapping to the respective view components

        val showLoadingEffect = intent.getBooleanExtra("showLoadingEffect", false)
        if (showLoadingEffect) {
            // Get the root view
            val rootView = findViewById<View>(R.id.rootView)

            // Load the animation
            val slideInRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)

            // Start the animation
            rootView.startAnimation(slideInRightAnimation)
        }

        val tvCity = findViewById<TextView>(R.id.tvCity);
        tvCity.text = city;
        val  temperature_details = findViewById<TextView>(R.id.tvTemperature)
        temperature_details.text = "$temperature C"
        val condition_image = findViewById<ImageView>(R.id.ivWeather)
        condition_image.setBackgroundResource(R.drawable.image_border)

        // Prepend "https:" to the conditionIcon URL if it starts with "//"
        val conditionIconUrl = if (conditionIcon?.startsWith("//") == true) "https:$conditionIcon" else conditionIcon
        // Load the image into the ImageView using Glide
        Glide.with(this).load(conditionIconUrl).into(condition_image)

        val condition_text = findViewById<TextView>(R.id.tvConditionText)
        condition_text.text = conditionText

        val UV_Index = findViewById<TextView>(R.id.tvUVIndex)
        UV_Index.text = "UV_Index:          $UvIndex"

        val Wind_speed = findViewById<TextView>(R.id.tvWindSpeed)
        Wind_speed.text = "Wind_Speed:      $windMph mph"

        val humidity_details = findViewById<TextView>(R.id.tvHumidity);
        humidity_details.text = "Humidity:       $humidity %"
    }
    }




