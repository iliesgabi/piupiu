package com.piu.solar_energy_usage.car

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.piu.solar_energy_usage.R
import java.util.*

class CarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val btnBack = findViewById<Button>(R.id.btn_back_car)
        btnBack.setOnClickListener {
            finish()
        }

        val speed = findViewById<TextView>(R.id.speedTextView)
        val hour = findViewById<TextView>(R.id.hoursTextView)

        val refresh = Runnable {
            val value = (500..1000).random()
            val speedValue = "" + value + "W"

            speed.text = speedValue

            if (value > 700) {
                val time = (5..7).random()
                val timeValue = "" + time + " hours remaining"

                hour.text = timeValue
            } else {
                val time = (7..10).random()
                val timeValue = "" + time + " hours remaining"

                hour.text = timeValue
            }
        }

        val updateThread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(700)
                        runOnUiThread(refresh)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        updateThread.start()

    }
}