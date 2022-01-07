package com.piu.solar_energy_usage.maintenance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R

class PanelsMaintenance : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R .layout.activity_panels_maintenance)

        val btnBack = findViewById<Button>(R.id.btn_back_maintenance)
        btnBack.setOnClickListener {
            finish()
        }

        val view = findViewById<RecyclerView>(R.id.maintenanceList)

        layoutManager = LinearLayoutManager(this)
        view.layoutManager = layoutManager

        adapter = PanelsMaintenanceViewAdapter()
        view.adapter = adapter

    }
}