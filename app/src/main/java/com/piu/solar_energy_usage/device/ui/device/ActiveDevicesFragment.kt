package com.piu.solar_energy_usage.device.ui.device

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.ui.adapter.ActiveDeviceAdapter
import com.piu.solar_energy_usage.device.ui.device_details.DeviceDetailsActivity

class ActiveDevicesFragment : Fragment() {

    private lateinit var deviceView: View
    private lateinit var deviceContext: Context

    private lateinit var deviceAdapter: ActiveDeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        deviceView = inflater.inflate(R.layout.fragment_active_devices, container, false)
        deviceContext = deviceView.context

        return deviceView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRecyclerViewAdapter()
        insertDevices()
    }

    private fun addRecyclerViewAdapter() {
        val devicesRecyclerView = deviceView.findViewById<RecyclerView>(R.id.activeDevicesRecyclerView)
        deviceAdapter = ActiveDeviceAdapter(context = deviceContext as Activity)
        devicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                deviceContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = deviceAdapter
        }
    }

    private fun insertDevices() {
        val activeDevices = DeviceDataSource.devices!!.filter { d -> d.isActive }
        deviceAdapter.setDataSource(activeDevices)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            DeviceDetailsActivity.ACTIVITY_ID -> {
                if(resultCode == AppCompatActivity.RESULT_OK) {
                    if(data == null) return

                    val device = data.getSerializableExtra("device") as Device
                    deviceAdapter.notifyDeviceChange(device)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}