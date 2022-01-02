package com.piu.solar_energy_usage.device.ui.statistics

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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.piu.solar_energy_usage.R
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class MonthFragment : Fragment() {

    private lateinit var monthConsumption: TextView
    private lateinit var barChart: BarChart
    private lateinit var monthDatePicker: TextView

    private var deviceId: UUID? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = inflater.inflate(R.layout.fragment_month, container, false)

        val bundle = arguments
        val bundleDeviceId: Serializable? = bundle!!.getSerializable("deviceId")
        if(bundleDeviceId != null)
            deviceId = bundleDeviceId as UUID

        monthConsumption = context.findViewById(R.id.monthConsumption)
        barChart = context.findViewById(R.id.monthBarChart)
        monthDatePicker = context.findViewById(R.id.monthDatePicker)
        monthDatePicker.text = getPrettyDate(Date())

        setDatePicker()
        initBarChart()
        setDataToBarChart(Calendar.getInstance())

        return context
    }

    private fun setDatePicker() {
        monthDatePicker.setOnClickListener {
            val picker = MonthYearPickerDialog()
            picker.setListener { _, year: Int, month: Int, day: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                monthDatePicker.text = getPrettyDate(calendar.time)
                setDataToBarChart(calendar)
            }

            picker.show(childFragmentManager, "MonthYearPickerDialog")
        }
    }

    private fun initBarChart() {
        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.marker = MonthMakerView(requireContext(), R.layout.statistics_maker_view)

        val xAxis: XAxis = barChart.xAxis
        xAxis.valueFormatter = XAxisFormatter()
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun setDataToBarChart(calendar: Calendar) {
        barChart.clear()

        val entries: ArrayList<BarEntry> = ArrayList()

        val noDays = calendar.getActualMaximum(Calendar.DATE)

        var consumption = 0f
        if(calendar.time > Calendar.getInstance().time) {
            for(i in 0 until noDays) {
                entries.add(BarEntry(i.toFloat(), 0f))
            }
        } else {
            for(i in 0 until noDays) {
                val value = (10..20).random().toFloat()
                entries.add(BarEntry(i.toFloat(), value))
                consumption += value
            }
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.color = Color.parseColor("#5584AC")
        barDataSet.setDrawValues(false)
        barChart.data = BarData(barDataSet)

        barChart.invalidate()

        monthConsumption.text = "$consumption kWh"
    }

    inner class XAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return (index + 1).toString()
        }
    }

    inner class MonthMakerView(context: Context, layoutResource: Int) :
        MarkerView(context, layoutResource) {


        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            val content = findViewById<TextView>(R.id.makerViewContent)

            content.text = "${e!!.y} kWh"

            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height - 20).toFloat())
        }
    }

    private fun getPrettyDate(date: Date): String {
        var formatter = SimpleDateFormat("MMMM yyyy")
        return formatter.format(date)
    }
}