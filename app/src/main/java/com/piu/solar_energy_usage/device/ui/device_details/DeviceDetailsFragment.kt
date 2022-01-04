package com.piu.solar_energy_usage.device.ui.device_details

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.ui.add_device.AddDeviceActivity
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.data.DevicesTypes
import java.lang.ClassCastException

class DeviceDetailsFragment : Fragment() {

    private lateinit var eventListener: EventListener
    private lateinit var detailsView: View
    private lateinit var detailsContext: Context

    private lateinit var device: Device

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        detailsView = inflater.inflate(R.layout.fragment_device_details, container, false)
        detailsContext = detailsView.context

        val bundle = arguments
        device = bundle!!.getSerializable("device") as Device

        return detailsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        addButtonsListeners()
    }

    interface EventListener {
        fun sendData(device: Device)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            eventListener = activity as EventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again")
        }
    }

    private fun initView() {
        val deviceName = detailsView.findViewById<TextView>(R.id.deviceNameDetails)
        deviceName.text = device.name

        val room = DeviceDataSource.getRoomById(device.roomId)
        val deviceRoom = detailsView.findViewById<TextView>(R.id.deviceRoomDetails)
        deviceRoom.text = room?.name

        val deviceTypeDetails = DevicesTypes.getDeviceTypeDetails(device.type)
        val deviceIcon = detailsView.findViewById<ImageView>(R.id.deviceIconDetails)
        deviceIcon.setImageResource(deviceTypeDetails.icon)

        setTurnOnOffButtonState(device.isActive)
    }

    private fun addButtonsListeners() {
        addEditButtonListener()
        addTurnOnOffButtonListener()
    }

    private fun addEditButtonListener() {
        val editButton = detailsView.findViewById<MaterialButton>(R.id.deviceEditButton)
        editButton.setOnClickListener {
            val intent = Intent(detailsContext, AddDeviceActivity::class.java)
            intent.putExtra("device", device)

            val room = DeviceDataSource.getRoomById(device.roomId)
            intent.putExtra("room", room)

            startActivityForResult(intent, AddDeviceActivity.ACTIVITY_ID)
        }
    }

    private fun addTurnOnOffButtonListener() {
        val turnOnOfButton = detailsView.findViewById<MaterialButton>(R.id.turnOnOffDetails)
        turnOnOfButton.setOnClickListener{
            if(device.isActive) {
                device.isActive = false
                Toast.makeText(detailsContext, "Device is turned off", Toast.LENGTH_SHORT).show()
            } else {
                device.isActive = true
                Toast.makeText(detailsContext, "Device is turned on", Toast.LENGTH_SHORT).show()
            }

            setTurnOnOffButtonState(device.isActive)
            DeviceDataSource.setDeviceState(device.id, device.isActive)
            eventListener.sendData(device)
        }
    }

    private fun setTurnOnOffButtonState(isActive: Boolean) {

        val turnOnOfButton = detailsView.findViewById<MaterialButton>(R.id.turnOnOffDetails)
        if(isActive) {
            turnOnOfButton.text = "Turn OFF"
            val color = Color.parseColor("#CD0C0C")
            turnOnOfButton.setTextColor(color)
            turnOnOfButton.strokeColor = ColorStateList.valueOf(color)
        } else {
            turnOnOfButton.text = "Turn ON"
            val color = Color.parseColor("#3E8E7E")
            turnOnOfButton.setTextColor(color)
            turnOnOfButton.strokeColor = ColorStateList.valueOf(color)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            AddDeviceActivity.ACTIVITY_ID -> {
                if(resultCode == AppCompatActivity.RESULT_OK) {
                    if(data == null) return

                    device = data.getSerializableExtra("device") as Device
                    initView()
                    eventListener.sendData(device)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}