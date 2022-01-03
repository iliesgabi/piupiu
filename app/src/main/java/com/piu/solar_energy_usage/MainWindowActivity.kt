package com.piu.solar_energy_usage

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.piu.solar_energy_usage.car.CarActivity
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

    fun onLogOutButtonClicked(item: MenuItem) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("You are about to log out!")
            .setMessage("Are you sure?")
            .setPositiveButton("Logout") { _, _ ->
                finish()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("You are about to log out!")
            .setMessage("Are you sure?")
            .setPositiveButton("Logout") { _, _ ->
                finish()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}