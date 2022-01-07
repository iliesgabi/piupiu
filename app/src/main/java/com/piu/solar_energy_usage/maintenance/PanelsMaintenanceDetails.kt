package com.piu.solar_energy_usage.maintenance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.piu.solar_energy_usage.R

class PanelsMaintenanceDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panels_maintenance_details)

        val btnBack = findViewById<Button>(R.id.btn_back_maintenance_details)
        btnBack.setOnClickListener {
            finish()
        }

        val titleView = findViewById<TextView>(R.id.maintenanceDetailsTitle)
        val shortDescriptionView = findViewById<TextView>(R.id.maintenanceDetailsDescription)
        val linkButtonView = findViewById<Button>(R.id.maintenanceDetailsButton)

        var title = intent.getStringExtra("maintenanceTitle")
        var description = intent.getStringExtra("maintenanceDescription")
        var link = intent.getStringExtra("maintenanceLink")

        titleView.text = title
        shortDescriptionView.text = description

        linkButtonView.setOnClickListener {
            var uri = Uri.parse(link)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}