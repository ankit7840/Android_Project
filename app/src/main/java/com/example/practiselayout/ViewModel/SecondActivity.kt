package com.example.practiselayout.ViewModel

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practiselayout.Model.CityData
import com.example.practiselayout.R

class secondActivity : AppCompatActivity() {

    private lateinit var city1Data: CityData
    private lateinit var city2Data: CityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        city1Data = CityData.fromIntent(intent, "1")
        city2Data = CityData.fromIntent(intent, "2")

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)

        if (intent.getBooleanExtra("showLoadingEffect", false)) {
            loadAnimation(textView1, 1500)
            loadAnimation(textView2, 2000)
        }

        setCityData(textView1, city1Data)
        setCityData(textView2, city2Data)
    }

    private fun loadAnimation(textView: TextView, duration: Long) {
        val animator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f)
        animator.duration = duration
        animator.start()
    }

    private fun setCityData(textView: TextView, cityData: CityData) {
        textView.apply {
            text = cityData.displayText
            setOnClickListener { startDetailActivity(cityData) }
        }
    }

    private fun startDetailActivity(cityData: CityData) {
        val intent = cityData.toIntent(this, detail::class.java)
        startActivity(intent)
    }
}