package com.piu.solar_energy_usage.device.ui.device

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.ui.statistics.StatisticsFragment

class DevicesStatisticsFragment : Fragment() {

    private lateinit var statisticsView: View
    private lateinit var statisticsContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        statisticsView = inflater.inflate(R.layout.fragment_devices_statistics, container, false)
        statisticsContext = statisticsView.context

        loadFragments()

        return statisticsView
    }

    private fun loadFragments() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.statisticsFragment, StatisticsFragment())
        transaction.add(R.id.statisticsRoomsFragment, RoomsFragment())
        transaction.commit()
    }
}