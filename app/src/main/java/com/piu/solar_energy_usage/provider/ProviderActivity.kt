package com.piu.solar_energy_usage.provider

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.provider.model.Provider
import com.piu.solar_energy_usage.provider.model.ProviderAdapter
import java.util.*
import kotlin.concurrent.schedule

class ProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        val backBtn = findViewById<Button>(R.id.btn_back_provider)
        backBtn.setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_provider)
        val progressBar = findViewById<ProgressBar>(R.id.pb_provider)

        Timer().schedule(delay = 1000) {
            Handler(mainLooper).post {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }

        val providerAdapter = ProviderAdapter(Provider.getProvidersFromFile("providers.json", this))

        recyclerView.apply {
            adapter = providerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}