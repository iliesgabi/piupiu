package com.piu.solar_energy_usage.device

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.room.AddRoomActivity
import com.piu.solar_energy_usage.device.room.Room
import com.piu.solar_energy_usage.device.room.RoomAdapter
import java.util.*

class DeviceActivity : AppCompatActivity() {

    private lateinit var roomAdapter: RoomAdapter
    private lateinit var deviceAdapter: DeviceAdapter

    private val addRoomActivityId = 1001
    private val addDeviceActivityId = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        addRecyclerViewsAdapters()
        addButtonsListeners()

        getRooms()
        getDevices()
        insertRooms()
    }

    private fun addRecyclerViewsAdapters() {
        val devicesRecyclerView = findViewById<RecyclerView>(R.id.devicesRecyclerView)
        deviceAdapter = DeviceAdapter(context = this)
        devicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = deviceAdapter
        }

        val roomsRecyclerView = findViewById<RecyclerView>(R.id.roomsRecyclerView)
        roomAdapter = RoomAdapter(context = this, deviceAdapter = deviceAdapter)
        roomsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = roomAdapter
        }
    }

    private fun addButtonsListeners() {

        val addRoomButton = findViewById<FloatingActionButton>(R.id.addRoom)
        addRoomButton.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            (this as Activity).startActivityForResult(intent, addRoomActivityId)
        }

        val addDeviceButton = findViewById<FloatingActionButton>(R.id.addDevice)
        addDeviceButton.setOnClickListener {
            val intent = Intent(this, AddDeviceActivity::class.java)
            intent.putExtra("room", roomAdapter.currentRoom)
            (this as Activity).startActivityForResult(intent, addDeviceActivityId)
        }
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

    private fun insertRooms() {
        roomAdapter.setDataSource(DeviceDataSource.rooms!!)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.editRoom -> {
                val intent = Intent(this, AddRoomActivity::class.java)
                intent.putExtra("roomPos", item.groupId)
                intent.putExtra("room", roomAdapter.currentRoom)
                startActivityForResult(intent, addRoomActivityId)
            }

            R.id.deleteRoom -> {
                val builder =  AlertDialog.Builder(this)
                builder.setTitle("Please Confirm")
                    .setMessage("Are you sure you want to delete this room?")
                    .setPositiveButton("Delete") { _, _ ->
                        val room: Room = roomAdapter.currentRoom

                        DeviceDataSource.rooms!!.removeAt(item.groupId)
                        DeviceDataSource.devices?.removeAll { device -> device.roomId ==  room.id}
                        
                        roomAdapter.deleteRoom(position = item.groupId)

                        Toast.makeText(
                            applicationContext,
                            "Room removed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Cancel", null)
                    .create().show()
            }

            R.id.editDevice -> {
                val intent = Intent(this, AddDeviceActivity::class.java)
                intent.putExtra("device", deviceAdapter.getDevice(item.groupId))
                intent.putExtra("room", roomAdapter.currentRoom)
                startActivityForResult(intent, addDeviceActivityId)
            }

            R.id.deleteDevice -> {
                val builder =  AlertDialog.Builder(this)
                builder.setTitle("Please Confirm")
                    .setMessage("Are you sure you want to delete this device?")
                    .setPositiveButton("Delete") { _, _ ->

                        DeviceDataSource.devices!!.removeAt(item.groupId)

                        deviceAdapter.deleteDevice(position = item.groupId)

                        Toast.makeText(
                            applicationContext,
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
            addRoomActivityId -> {
                if(resultCode == RESULT_OK) {
                    if(data == null) return

                    val roomPos = data.getIntExtra("roomPos", -1)
                    val room = data.getSerializableExtra("room") as Room

                    if(roomPos != -1) {
                        roomAdapter.notifyRoomChange(roomPos, room)
                    } else {
                        roomAdapter.insertRoom(room)
                    }
                }
            }

            addDeviceActivityId -> {
                if(resultCode == RESULT_OK) {
                    if(data == null) return

                    val roomId = data.getSerializableExtra("roomId") as UUID
                    roomAdapter.selectRoom(roomId)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}