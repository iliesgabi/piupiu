package com.piu.solar_energy_usage.device.ui.device

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.model.Room
import com.piu.solar_energy_usage.device.ui.device_details.DeviceDetailsActivity

class DeviceActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val btnBack = findViewById<Button>(R.id.btn_back_devices)
        btnBack.setOnClickListener {
            finish()
        }

        getRooms()
        getDevices()

        loadFragment(DeviceFragment())
        addNavigationViewListener()
    }

    private fun getRooms() {
        if(DeviceDataSource.rooms == null) {
            DeviceDataSource.rooms = Room.getRoomsFromFile("rooms.json", this).toMutableList()
        }
    }

    private fun getDevices() {
        if(DeviceDataSource.devices == null) {
            DeviceDataSource.devices = Device.getDevicesFromFile("devices.json", this).toMutableList()
        }
    }

    private fun addNavigationViewListener() {
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.deviceDetails -> {
                    loadFragment(DeviceFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.activeDevices -> {
                    loadFragment(ActiveDevicesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.deviceStatistics -> {
                    loadFragment(DevicesStatisticsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.deviceFragment, fragment)
            .commit()

        currentFragment = fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode) {
            DeviceDetailsActivity.ACTIVITY_ID -> {
               currentFragment.onActivityResult(requestCode, resultCode, data)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}