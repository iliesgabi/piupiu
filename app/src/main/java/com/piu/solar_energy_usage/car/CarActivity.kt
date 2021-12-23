package com.piu.solar_energy_usage.car

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.piu.solar_energy_usage.R
import java.util.*

class CarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val speed = findViewById<TextView>(R.id.speedTextView)
        val hour = findViewById<TextView>(R.id.hoursTextView)

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val value = (500..1000).random()
                val speedValue = "" + value + "W"

                speed.text = speedValue

                val time = (5..10).random()
                val timeValue = "" + time + " hours remaining"

                hour.text = timeValue
            }
        }, 0, 700)

    }
}