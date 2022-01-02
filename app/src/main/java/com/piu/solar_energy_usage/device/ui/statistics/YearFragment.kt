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
import java.util.*


class YearFragment : Fragment() {

    private lateinit var yearConsumption: TextView
    private lateinit var barChart: BarChart
    private lateinit var yearDatePicker: TextView
    private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var deviceId: UUID? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = inflater.inflate(R.layout.fragment_year, container, false)

        val bundle = arguments
        val bundleDeviceId: Serializable? = bundle!!.getSerializable("deviceId")
        if(bundleDeviceId != null)
            deviceId = bundleDeviceId as UUID

        yearConsumption = context.findViewById(R.id.yearConsumption)
        barChart = context.findViewById(R.id.yearBarChart)
        yearDatePicker = context.findViewById(R.id.yearDatePicker)

        val calendar = Calendar.getInstance()
        yearDatePicker.text = calendar.get(Calendar.YEAR).toString()

        setDatePicker()
        initBarChart()
        setDataToBarChart()

        return context
    }

    private fun setDatePicker() {
        yearDatePicker.setOnClickListener {
            val picker = YearPickerDialog()
            picker.setListener { _, year: Int, _: Int, _: Int ->
                yearDatePicker.text = year.toString()
                setDataToBarChart()
            }

            picker.show(childFragmentManager, "YearPickerDialog")
        }
    }

    private fun initBarChart() {
        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.marker = YearMakerView(requireContext(), R.layout.statistics_maker_view)

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
        for(i in 0..11) {
            val value = (20..50).random().toFloat()
            entries.add(BarEntry(i.toFloat(), value))
            consumption += value
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.color = Color.parseColor("#5584AC")
        barDataSet.setDrawValues(false)
        barChart.data = BarData(barDataSet)

        barChart.invalidate()

        yearConsumption.text = "$consumption kWh"
    }

    inner class XAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return months[index]
        }
    }

    inner class YearMakerView(context: Context, layoutResource: Int) :
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
}