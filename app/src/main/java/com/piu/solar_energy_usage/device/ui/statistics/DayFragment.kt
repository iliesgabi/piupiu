package com.piu.solar_energy_usage.device.ui.statistics

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Device
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DayFragment : Fragment() {

    private lateinit var dayConsumption: TextView
    private lateinit var barChart: BarChart
    private lateinit var dayDatePicker: TextView

    private var deviceId: UUID? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = inflater.inflate(R.layout.fragment_day, container, false)

        val bundle = arguments
        val bundleDeviceId: Serializable? = bundle!!.getSerializable("deviceId")
        if(bundleDeviceId != null)
            deviceId = bundleDeviceId as UUID

        dayConsumption = context.findViewById(R.id.dayConsumption)
        barChart = context.findViewById(R.id.dayBarChart)
        dayDatePicker = context.findViewById(R.id.dayDatePicker)
        dayDatePicker.text = getPrettyDate(Date())

        setDatePicker()
        initBarChart()
        setDataToBarChart()

        return context
    }

    private fun setDatePicker() {
        dayDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val picker = DatePickerDialog(
                requireContext(),
                { _, year: Int, month: Int, day: Int ->

                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    dayDatePicker.text = getPrettyDate(calendar.time)
                    setDataToBarChart()
                },
                year, month, day
            )

            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }
    }

    private fun initBarChart() {
        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.marker = DayMakerView(requireContext(), R.layout.statistics_maker_view)

        val xAxis: XAxis = barChart.xAxis
        xAxis.valueFormatter = XAxisFormatter()
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun setDataToBarChart() {
        barChart.clear()

        val entries: ArrayList<BarEntry> = ArrayList()

        var consumption = 0f
        for(i in 0..23) {
            val value = (5..10).random().toFloat()
            entries.add(BarEntry(i.toFloat(), value))
            consumption += value
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.color = Color.parseColor("#5584AC")
        barDataSet.setDrawValues(false)
        barChart.data = BarData(barDataSet)

        barChart.invalidate()

        dayConsumption.text = "$consumption kWh"
    }

    inner class XAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()

            return getPrettyHour(index)
        }
    }

    inner class DayMakerView(context: Context, layoutResource: Int) :
        MarkerView(context, layoutResource) {

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            val content = findViewById<TextView>(R.id.makerViewContent)

            content.text = "${getPrettyHour(e!!.x.toInt())}\n${e!!.y} kWh"

            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height - 20).toFloat())
        }
    }

    private fun getPrettyHour(hour: Int): String {
        if(hour == 0)
            return "12 AM"

        if(hour in 1..11)
            return "$hour AM"

        if(hour == 12)
            return "12 PM"

        if(hour in 13..23)
            return "${hour - 12} PM"

        return ""
    }

    private fun getPrettyDate(date: Date): String {
        var formatter = SimpleDateFormat("dd MMMM yyyy")
        return formatter.format(date)
    }
}