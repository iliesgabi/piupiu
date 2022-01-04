package com.piu.solar_energy_usage.device.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.ui.adapter.StatisticsViewPagerAdapter
import com.piu.solar_energy_usage.device.ui.statistics.DayFragment
import com.piu.solar_energy_usage.device.ui.statistics.MonthFragment
import com.piu.solar_energy_usage.device.ui.statistics.YearFragment

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = inflater.inflate(R.layout.fragment_statistics, container, false)

        var device: Device? = null
        if(arguments != null) {
            val bundleDevice = requireArguments().getSerializable("device")
            if(bundleDevice != null)
                device = bundleDevice as Device
        }

        val viewPagerAdapter = StatisticsViewPagerAdapter(supportFragmentManager = parentFragmentManager)

        val bundle = Bundle()
        if(device != null)
            bundle.putSerializable("deviceId", device.id)

        val dayFragment = DayFragment()
        dayFragment.arguments = bundle
        viewPagerAdapter.addFragment(dayFragment, "Day")

        val monthFragment = MonthFragment()
        monthFragment.arguments = bundle
        viewPagerAdapter.addFragment(monthFragment, "Month")

        val yearFragment = YearFragment()
        yearFragment.arguments = bundle
        viewPagerAdapter.addFragment(yearFragment, "Year")

        val viewPager = context.findViewById<ViewPager>(R.id.statisticsViewPager)
        viewPager.adapter = viewPagerAdapter

        val tabLayout = context.findViewById<TabLayout>(R.id.statisticsTabLayout)
        tabLayout.setupWithViewPager(viewPager)

        return context
    }
}