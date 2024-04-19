package com.example.practiselayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.practiselayout.CityData.Companion.fromIntent

@Suppress("DEPRECATION")
class secondActivity : AppCompatActivity() {

    private lateinit var city1Data: CityData
    private lateinit var city2Data: CityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        city1Data = fromIntent(intent, "1")
        city2Data = fromIntent(intent, "2")

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
//
//
        val showLoadingEffect = intent.getBooleanExtra("showLoadingEffect", false)
        if (showLoadingEffect) {
            // Create an ObjectAnimator that animates the alpha property of the TextView
            val animator1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f, 1f)
            val animator2 = ObjectAnimator.ofFloat(textView2, "alpha", 0f, 1f)
            animator1.duration = 1500 // Duration of 1.5 seconds
            animator2.duration = 2000 // Duration of 2 seconds

            // Start the animations
            animator1.start()
            animator2.start()
        }

        textView1.apply {
            text = city1Data.displayText
            setOnClickListener { startDetailActivity(city1Data) }
        }

        textView2.apply {
            text = city2Data.displayText
            setOnClickListener { startDetailActivity(city2Data) }
        }
    }

    private fun startDetailActivity(cityData: CityData) {

        val intent = cityData.toIntent(this, detail::class.java)
        startActivity(intent)
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }
}

data class CityData(
    val windMph: Double,
    val conditionText: String?,
    val conditionIcon: String?,
    val temperature: Double,
    val humidity: Int,
    val UVindex: Double,
    val city: String?,
    val showloadingEffect: Boolean? = false
) {
    val displayText: String
        get() = "City:$city\nTemperature:$temperature C"

    fun toIntent(context: Context, cls: Class<*>): Intent {
        return Intent(context, cls).apply {
            putExtra("windMph", windMph)
            putExtra("conditionText", conditionText)
            putExtra("conditionIcon", conditionIcon)
            putExtra("temperature", temperature)
            putExtra("humidity", humidity)
            putExtra("UVIndex", UVindex)
            putExtra("city", city)
            putExtra("showLoadingEffect", showloadingEffect)
        }
    }

    companion object {
        fun fromIntent(intent: Intent, cityNumber: String): CityData {
            return CityData(
                windMph = intent.getDoubleExtra("windMph$cityNumber", 0.0),
                conditionText = intent.getStringExtra("conditionText$cityNumber"),
                conditionIcon = intent.getStringExtra("conditionIcon$cityNumber"),
                temperature = intent.getDoubleExtra("temperature$cityNumber", Double.NaN),
                humidity = intent.getIntExtra("humidity$cityNumber", 0),
                UVindex = intent.getDoubleExtra("UVIndex$cityNumber", 0.0),
                city = intent.getStringExtra("city$cityNumber")

            )
        }
    }
}