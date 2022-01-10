package com.piu.solar_energy_usage.device.ui.device

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.ui.adapter.DeviceAdapter
import com.piu.solar_energy_usage.device.ui.add_device.AddDeviceActivity
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.ui.device_details.DeviceDetailsActivity
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.ui.add_room.AddRoomActivity
import com.piu.solar_energy_usage.device.model.Room
import com.piu.solar_energy_usage.device.ui.adapter.RoomAdapter
import java.util.*

class DeviceFragment : Fragment() {

    private lateinit var deviceView: View
    private lateinit var deviceContext: Context

    private lateinit var roomAdapter: RoomAdapter
    private lateinit var deviceAdapter: DeviceAdapter

    private lateinit var roomsRecyclerView: RecyclerView
    private lateinit var devicesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        deviceView = inflater.inflate(R.layout.fragment_device, container, false)
        deviceContext = deviceView.context

        return deviceView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRecyclerViewsAdapters()
        addButtonsListeners()

        insertRooms()
    }

    private fun addRecyclerViewsAdapters() {
        devicesRecyclerView = deviceView.findViewById(R.id.devicesRecyclerView)
        deviceAdapter = DeviceAdapter(context = deviceContext as Activity)
        devicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                deviceContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = deviceAdapter
        }

        roomsRecyclerView = deviceView.findViewById(R.id.roomsRecyclerView)
        roomAdapter = RoomAdapter(context = deviceContext as Activity, deviceAdapter = deviceAdapter)
        roomsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                deviceContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = roomAdapter
        }
    }

    private fun addButtonsListeners() {

        val addRoomButton = deviceView.findViewById<FloatingActionButton>(R.id.addRoom)
        addRoomButton.setOnClickListener {
            val intent = Intent(deviceContext, AddRoomActivity::class.java)
            startActivityForResult(intent, AddRoomActivity.ACTIVITY_ID)
        }

        val addDeviceButton = deviceView.findViewById<FloatingActionButton>(R.id.addDevice)
        addDeviceButton.setOnClickListener {
            val intent = Intent(deviceContext, AddDeviceActivity::class.java)
            intent.putExtra("room", roomAdapter.currentRoom)
            startActivityForResult(intent, AddDeviceActivity.ACTIVITY_ID)
        }
    }

    private fun insertRooms() {
        roomAdapter.setDataSource(DeviceDataSource.rooms!!)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.editRoom -> {
                val intent = Intent(deviceContext, AddRoomActivity::class.java)
                intent.putExtra("roomPos", item.groupId)
                intent.putExtra("room", roomAdapter.currentRoom)
                startActivityForResult(intent, AddRoomActivity.ACTIVITY_ID)
            }

            R.id.deleteRoom -> {
                val builder =  AlertDialog.Builder(deviceContext)
                builder.setTitle("Please Confirm")
                    .setMessage("Are you sure you want to delete this room?\nAll associated devices will be deleted!")
                    .setPositiveButton("Delete") { _, _ ->
                        val room: Room = roomAdapter.getRoom(item.groupId)

                        DeviceDataSource.rooms!!.removeAt(item.groupId)
                        DeviceDataSource.devices?.removeAll { device -> device.roomId ==  room.id}

                        roomAdapter.deleteRoom(position = item.groupId)

                        Toast.makeText(
                            deviceContext,
                            "Room removed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Cancel", null)
                    .create().show()
            }

            R.id.editDevice -> {
                val intent = Intent(deviceContext, AddDeviceActivity::class.java)
                intent.putExtra("device", deviceAdapter.getDevice(item.groupId))
                intent.putExtra("room", roomAdapter.currentRoom)
                startActivityForResult(intent, AddDeviceActivity.ACTIVITY_ID)
            }

            R.id.deleteDevice -> {
                val builder =  AlertDialog.Builder(deviceContext)
                builder.setTitle("Please Confirm")
                    .setMessage("Are you sure you want to delete this device?")
                    .setPositiveButton("Delete") { _, _ ->

                        val device = deviceAdapter.getDevice(item.groupId)
                        DeviceDataSource.deleteDevice(deviceId = device.id)

                        deviceAdapter.deleteDevice(position = item.groupId)

                        Toast.makeText(
                            deviceContext,
                            "Device removed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Cancel", null)
                    .create().show()
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            AddRoomActivity.ACTIVITY_ID -> {
                if(resultCode == AppCompatActivity.RESULT_OK) {
                    if(data == null) return

                    val roomPos = data.getIntExtra("roomPos", -1)
                    val room = data.getSerializableExtra("room") as Room

                    if(roomPos != -1) {
                        roomAdapter.notifyRoomChange(roomPos, room)
                        roomsRecyclerView.scrollToPosition(roomPos)
                    } else {
                        roomAdapter.insertRoom(room)
                        roomsRecyclerView.scrollToPosition(roomAdapter.itemCount - 1)
                    }
                }
            }

            AddDeviceActivity.ACTIVITY_ID -> {
                if(resultCode == AppCompatActivity.RESULT_OK) {
                    if(data == null) return

                    val roomId = data.getSerializableExtra("roomId") as UUID
                    val roomPos = roomAdapter.selectRoom(roomId)
                    if(roomPos != -1) roomsRecyclerView.scrollToPosition(roomPos)
                }
            }

            DeviceDetailsActivity.ACTIVITY_ID -> {
                if(resultCode == AppCompatActivity.RESULT_OK) {
                    if(data == null) return

                    val device = data.getSerializableExtra("device") as Device
                    val roomPos = roomAdapter.selectRoom(device.roomId)
                    if(roomPos != -1) roomsRecyclerView.scrollToPosition(roomPos)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}