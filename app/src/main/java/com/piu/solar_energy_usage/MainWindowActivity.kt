package com.piu.solar_energy_usage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.piu.solar_energy_usage.car.CarActivity
import com.piu.solar_energy_usage.device.ui.device.DeviceActivity
import com.piu.solar_energy_usage.invoice.InvoiceActivity
import com.piu.solar_energy_usage.meteo.WeatherActivity

class MainWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    fun onLogOutButtonClicked(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onWeatherButtonClicked(item: MenuItem) {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }

    fun onInvoiceButtonClicked(item: MenuItem) {
        val intent = Intent(this, InvoiceActivity::class.java)
        startActivity(intent)
    }

    fun onCarButtonClicked(item: MenuItem) {
        val intent = Intent(this, CarActivity::class.java)
        startActivity(intent)
    }

    fun onDevicesButtonClicked(item: MenuItem) {
        val intent = Intent(this, DeviceActivity::class.java)
        startActivity(intent)
    }

}