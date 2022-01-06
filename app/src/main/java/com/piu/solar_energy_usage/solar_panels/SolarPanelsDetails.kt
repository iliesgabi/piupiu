package com.piu.solar_energy_usage.solar_panels

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.piu.solar_energy_usage.R

class SolarPanelsDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_panels_details)

        val btnBack = findViewById<Button>(R.id.btn_back_solar_panels_details)
        btnBack.setOnClickListener {
            finish()
        }

        val titleView = findViewById<TextView>(R.id.solarPanelDetailsTitle)
        val shortDescriptionView = findViewById<TextView>(R.id.solarPanelShortDescription)
        val longDescriptionView = findViewById<TextView>(R.id.solarPanelLongDescription)

        var title = intent.getStringExtra("solarPanelTitle")
        var shortDescription = intent.getStringExtra("solarPanelShortDescription")
        var longDescription = intent.getStringExtra("solarPanelLargeDescription")

        titleView.text = title
        shortDescriptionView.text = shortDescription
        longDescriptionView.text = longDescription
    }
}