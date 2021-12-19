package com.piu.solar_energy_usage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.piu.solar_energy_usage.authentication.LogInActivity
import com.piu.solar_energy_usage.authentication.RegisterActivity
import com.piu.solar_energy_usage.meteo.WeatherActivity
import com.piu.solar_energy_usage.meteo.WeatherAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val logInButton = findViewById<Button>(R.id.logInButton)
//        logInButton.setOnClickListener {
//            val intent = Intent(this, LogInActivity::class.java)
//            startActivity(intent)
//        }
//
//        val registerButton = findViewById<Button>(R.id.registerButton)
//        registerButton.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }

        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }
}