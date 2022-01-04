package com.piu.solar_energy_usage

import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.appbar.MaterialToolbar
import com.piu.solar_energy_usage.car.CarActivity
import com.piu.solar_energy_usage.device.ui.device.DeviceActivity
import com.piu.solar_energy_usage.invoice.InvoiceActivity
import com.piu.solar_energy_usage.meteo.WeatherActivity
import com.piu.solar_energy_usage.provider.ProviderActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainWindowActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        val today = findViewById<TextView>(R.id.today)
        SimpleDateFormat("dd/M/yyyy").format(Date()).toString().also { today.text = it }

        pieChart = findViewById(R.id.pieChart)

        initPieChart()

        setDataToPieChart()
    }

    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""

        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)

        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    private fun setDataToPieChart() {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()

        val produced = 0.0f + Random.nextFloat() * (200.0f)
        val consumed = 0.0f + Random.nextFloat() * (700.0f)
        val wasted = 0.0f + Random.nextFloat() * (75.0f)

        dataEntries.add(PieEntry(produced, "Produced"))
        dataEntries.add(PieEntry(consumed, "Consumed"))
        dataEntries.add(PieEntry(wasted, "Wasted"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#18a1cd"))
        colors.add(Color.parseColor("#1d81a2"))
        colors.add(Color.parseColor("#15607a"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.holeRadius = 60f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        pieChart.setDrawCenterText(true);

        pieChart.centerText = "${consumed - produced + wasted} kWh"

        pieChart.invalidate()
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

    fun onProvidersButtonClicked(item: MenuItem) {
        val intent = Intent(this, ProviderActivity::class.java)
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

    fun onDevicesButtonClicked(item: MenuItem) {
        val intent = Intent(this, DeviceActivity::class.java)
        startActivity(intent)
    }

}