package com.example.practiselayout.ViewModel

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.practiselayout.R

class detail : AppCompatActivity() {
    private lateinit var tvCity: TextView
    private lateinit var temperatureDetails: TextView
    private lateinit var conditionImage: ImageView
    private lateinit var conditionText: TextView
    private lateinit var uvIndex: TextView
    private lateinit var windSpeed: TextView
    private lateinit var humidityDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        tvCity = findViewById(R.id.tvCity)
        temperatureDetails = findViewById(R.id.tvTemperature)
        conditionImage = findViewById(R.id.ivWeather)
        conditionText = findViewById(R.id.tvConditionText)
        uvIndex = findViewById(R.id.tvUVIndex)
        windSpeed = findViewById(R.id.tvWindSpeed)
        humidityDetails = findViewById(R.id.tvHumidity)

        if (intent.getBooleanExtra("showLoadingEffect", false)) {
            loadAnimation()
        }

        setWeatherDetails()
    }

    private fun loadAnimation() {
//        val rootView = findViewById<View>(R.id.rootView)
//        val slideInRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
//        rootView.startAnimation(slideInRightAnimation)
    }

    private fun setWeatherDetails() {

        val windMph = intent.getDoubleExtra("windMph", 0.11)
        val conditionTextExtra = intent.getStringExtra("conditionText")
        val conditionIcon = intent.getStringExtra("conditionIcon")
        val temperature = intent.getDoubleExtra("temperature", 0.0)
        val city = intent.getStringExtra("city")
        val uvIndexExtra = intent.getDoubleExtra("UVIndex", 0.0)
        val humidity = intent.getIntExtra("humidity", 0)

        tvCity.text = city
        temperatureDetails.text = String.format("%.1f C", temperature)
        loadImage(conditionIcon, conditionImage)
        conditionText.text = conditionTextExtra

        val backgroundTextureView = findViewById<TextureView>(R.id.textureView)
        val mediaPlayer = MediaPlayer()

        backgroundTextureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
                // Provide an empty implementation
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                // Provide an empty implementation
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                mediaPlayer.stop()
                mediaPlayer.release()
                return true
            }

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {

                val videoResource = when {
                    conditionTextExtra?.contains("Sunny", ignoreCase = true) == true -> R.raw.sunny
                    conditionTextExtra?.contains("Rainy", ignoreCase = true) == true -> R.raw.rainy
                    conditionTextExtra?.contains("overcast", ignoreCase = true) == true -> R.raw.cloudyorovercast
                    conditionTextExtra?.contains("cloudy", ignoreCase = true) == true -> R.raw.cloudyorovercast
                    conditionTextExtra?.contains("thunderstorm", ignoreCase = true) == true -> R.raw.thunderstorm
                    conditionTextExtra?.contains("drizzle", ignoreCase = true) == true -> R.raw.drizzle
                    conditionTextExtra?.contains("fog", ignoreCase = true) == true -> R.raw.fog
                    conditionTextExtra?.contains("snowy", ignoreCase = true) == true -> R.raw.snowy
                    conditionTextExtra?.contains("hail", ignoreCase = true) == true -> R.raw.hail
                    conditionTextExtra?.contains("partlycloudy", ignoreCase = true) == true -> R.raw.partlycloudy
                    else -> R.raw.default1
                }
                mediaPlayer.setDataSource(this@detail, Uri.parse("android.resource://" + packageName + "/" + videoResource))
                mediaPlayer.setSurface(Surface(surface))
                mediaPlayer.isLooping = true
                mediaPlayer.prepare()
                mediaPlayer.setOnPreparedListener { mediaPlayer.start() }
            }
        }
        uvIndex.text = String.format("UV_Index: %.2f", uvIndexExtra)
        windSpeed.text = String.format("Wind_Speed: %.2f mph", windMph)
        humidityDetails.text = String.format("Humidity: %d %%", humidity)

    }

    private fun loadImage(url: String?, imageView: ImageView) {
        val conditionIconUrl = if (url?.startsWith("//") == true) "https:$url" else url
        Glide.with(this).load(conditionIconUrl).into(imageView)
    }
}




