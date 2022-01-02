package com.piu.solar_energy_usage.device.ui.device_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.ui.statistics.StatisticsFragment

class DeviceDetailsActivity : AppCompatActivity(), DeviceDetailsFragment.EventListener {

    companion object {
        const val ACTIVITY_ID = 1003
    }

    private lateinit var device: Device

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        device = intent.getSerializableExtra("device") as Device

        loadFragments()
    }

    private fun loadFragments() {
        val detailsFragment = DeviceDetailsFragment()
        val bundle = Bundle()

        bundle.putSerializable("device", device)
        detailsFragment.arguments = bundle

        val statisticsFragment = StatisticsFragment()
        statisticsFragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.deviceDetailsFragment, detailsFragment)
        transaction.add(R.id.deviceStatisticsFragment, statisticsFragment)
        transaction.commit()
    }

    override fun sendData(device: Device) {
        this.device = device
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("device", device)
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
}