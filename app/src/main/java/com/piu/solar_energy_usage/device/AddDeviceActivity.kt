package com.piu.solar_energy_usage.device

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.room.Room
import java.util.*

class AddDeviceActivity : AppCompatActivity() {

    private var device: Device? = null
    private lateinit var room: Room
    private var selectedDeviceType: DeviceType? = null
    private var selectedDeviceRoom: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        val intentDevice = intent.getSerializableExtra("device")
        if(intentDevice != null)
            device = intentDevice as Device

        room = intent.getSerializableExtra("room") as Room

        addAutoCompleteOptions()
        addButtonsListeners()

        initView(device)
    }

    private fun addAutoCompleteOptions() {

        addDeviceTypeOptions()
        addDeviceRoomOptions()
    }

    private fun addDeviceTypeOptions() {
        val deviceType = findViewById<AutoCompleteTextView>(R.id.deviceTypeInput)
        val deviceTypeAdapter = AutoCompleteDeviceTypeAdapter(context = this, list = DevicesTypes.types)
        deviceType.setAdapter(deviceTypeAdapter)

        deviceType.setOnItemClickListener { parent, _, position, _ ->
            val deviceType = parent.getItemAtPosition(position) as DeviceType
            selectedDeviceType = deviceType
        }
    }

    private fun addDeviceRoomOptions() {
        val deviceRoom = findViewById<AutoCompleteTextView>(R.id.deviceRoomInput)
        val roomDataAdapter = AutoCompleteRoomAdapter(context = this, list = DeviceDataSource.rooms!!)
        deviceRoom.setAdapter(roomDataAdapter)

        deviceRoom.setOnItemClickListener { parent, _, position, _ ->
            val deviceRoom = parent.getItemAtPosition(position) as Room
            selectedDeviceRoom = deviceRoom
        }

        deviceRoom.setText(room.toString(), false)
        selectedDeviceRoom = room
    }

    private fun addButtonsListeners() {
        addAddButtonListener()
        addCancelButtonListener()
    }

    private fun addAddButtonListener() {
        val addButton = findViewById<Button>(R.id.addDeviceButton)
        addButton.setOnClickListener {
            var valid = true

            val deviceNameLayout = findViewById<TextInputLayout>(R.id.deviceNameLayout)
            val deviceName = findViewById<TextView>(R.id.deviceNameInput).text.toString()

            val deviceNameResult = validateDeviceName(deviceName)
            if(!deviceNameResult.first) {
                deviceNameLayout.isErrorEnabled = true
                valid = false

                when(deviceNameResult.second) {
                    0 -> { deviceNameLayout.error = "Required"}
                    1 -> { deviceNameLayout.error = "Too short"}
                }
            } else {
                deviceNameLayout.isErrorEnabled = false
            }

            val deviceTypeLayout = findViewById<TextInputLayout>(R.id.deviceTypeLayout)

            if(selectedDeviceType == null) {
                valid = false
                deviceTypeLayout.isErrorEnabled = true
                deviceTypeLayout.error = "Required"
            } else {
                deviceTypeLayout.isErrorEnabled = false
            }

            val deviceRoomLayout = findViewById<TextInputLayout>(R.id.deviceRoomLayout)

            if(selectedDeviceRoom == null) {
                valid = false
                deviceRoomLayout.error = "Required"
            } else {
                deviceRoomLayout.isErrorEnabled = false
            }

            if(valid) {

                if(device == null) {
                    device = Device(
                        id = UUID.randomUUID(),
                        name = deviceName,
                        roomId = selectedDeviceRoom!!.id,
                        type = selectedDeviceType!!.name
                    )
                } else {
                    device!!.name = deviceName
                    device!!.roomId = selectedDeviceRoom!!.id
                    device!!.type = selectedDeviceType!!.name
                }


                DeviceDataSource.insertDevice(device!!)
                val intent = Intent() //this, DeviceActivity::class.java
                intent.putExtra("roomId", device!!.roomId)
                setResult(RESULT_OK, intent)
                super.onBackPressed()
            }
        }
    }

    private fun addCancelButtonListener() {
        val cancelButton = findViewById<Button>(R.id.cancelDeviceButton)
        cancelButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView(device: Device?) {
        if(device == null)
            return

        val deviceName = findViewById<TextView>(R.id.deviceNameInput)
        val deviceType = findViewById<AutoCompleteTextView>(R.id.deviceTypeInput)
        val deviceRoom = findViewById<AutoCompleteTextView>(R.id.deviceRoomInput)

        deviceName.text = device.name
        deviceType.setText(device.type, false)
        deviceRoom.setText(room.name, false)

        val deviceTypeDetails = DevicesTypes.getDeviceTypeDetails(device.type)
        selectedDeviceType = deviceTypeDetails
        selectedDeviceRoom = room
    }

    override fun onBackPressed() {
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Please Confirm")
            .setMessage("Are you sure you don't want to save the device?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("No", null)
            .create().show()
    }

    private fun validateDeviceName(name: String): Pair<Boolean, Int> {
        if(name.isEmpty()) {
            return Pair(false, 0)
        }

        if(name.length < 2) {
            return Pair(false, 1)
        }

        return Pair(true, -1)
    }
}