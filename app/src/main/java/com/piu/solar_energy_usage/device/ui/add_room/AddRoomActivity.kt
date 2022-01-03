package com.piu.solar_energy_usage.device.ui.add_room

import com.piu.solar_energy_usage.device.model.RoomType
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.data.DeviceDataSource
import com.piu.solar_energy_usage.device.model.Room
import com.piu.solar_energy_usage.device.data.RoomTypes
import com.piu.solar_energy_usage.device.ui.adapter.AutoCompleteRoomTypeAdapter
import com.squareup.picasso.Picasso
import java.util.*

class AddRoomActivity : AppCompatActivity() {

    companion object {
        const val ACTIVITY_ID = 1001
    }

    private var room: Room? = null
    private var selectedRoomType: RoomType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        val btnBack = findViewById<Button>(R.id.btn_back_add_room)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val intentRoom = intent.getSerializableExtra("room")
        if(intentRoom != null)
            room = intentRoom as Room

        addAutoCompleteOptions()
        addButtonsListeners()

        initView(room)
    }

    private fun addAutoCompleteOptions() {
        val roomType = findViewById<AutoCompleteTextView>(R.id.roomTypeInput)
        val dataAdapter = AutoCompleteRoomTypeAdapter(context = this, list = RoomTypes.types)
        roomType.setAdapter(dataAdapter)

        roomType.setOnItemClickListener { parent, _, position, _ ->
            val room = parent?.getItemAtPosition(position) as RoomType
            selectedRoomType = room

            val roomImage = findViewById<ImageView>(R.id.roomTypeImage)
            Picasso.with(this)
                .load(room.image)
                .placeholder(R.mipmap.ic_launcher)
                .into(roomImage)
        }
    }

    private fun addButtonsListeners() {
        addAddButtonListener()
        addCancelButtonListener()
    }

    private fun addAddButtonListener() {
        val addButton = findViewById<Button>(R.id.addRoomButton)
        addButton.setOnClickListener {
            var valid = true

            val roomNameLayout = findViewById<TextInputLayout>(R.id.roomNameLayout)
            val roomName = findViewById<TextView>(R.id.roomNameInput).text.toString()

            val roomNameResult = validateRoomName(roomName)
            if(!roomNameResult.first) {
                valid = false
                roomNameLayout.isErrorEnabled = true

                when(roomNameResult.second) {
                    0 -> { roomNameLayout.error = "Required"}
                    1 -> { roomNameLayout.error = "Too short"}
                    2 -> { roomNameLayout.error = "This name already exists"}
                }
            } else {
                roomNameLayout.isErrorEnabled = false
            }

            val roomTypeLayout = findViewById<TextInputLayout>(R.id.roomTypeLayout)

            if(selectedRoomType == null) {
                valid = false
                roomNameLayout.isErrorEnabled = true
                roomTypeLayout.error = "Required"
            } else {
                roomTypeLayout.isErrorEnabled = false
            }

            if(valid) {
                if(room == null) {
                    room = Room(
                        id = UUID.randomUUID(),
                        name = roomName,
                        type = selectedRoomType!!.name
                    )
                } else {
                    room!!.name = roomName
                    room!!.type = selectedRoomType!!.name
                }

                DeviceDataSource.insertRoom(room!!)
                val intent = Intent() //this, DeviceActivity::class.java
                intent.putExtra("roomPos", this.intent.getIntExtra("roomPos", -1))
                intent.putExtra("room", room!!)
                setResult(RESULT_OK, intent)
                super.onBackPressed()
            }
        }
    }

    private fun addCancelButtonListener() {
        val cancelButton = findViewById<Button>(R.id.cancelRoomButton)
        cancelButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView(room: Room?) {
        if(room == null)
            return

        val roomName = findViewById<TextView>(R.id.roomNameInput)
        val roomType = findViewById<AutoCompleteTextView>(R.id.roomTypeInput)
        val roomImage = findViewById<ImageView>(R.id.roomTypeImage)

        roomName.text = room.name
        roomType.setText(room.type, false)

        val roomTypeDetails = RoomTypes.getRoomTypeDetails(room.type)
        Picasso.with(this)
            .load(roomTypeDetails.image)
            .placeholder(R.mipmap.ic_launcher)
            .into(roomImage)

        selectedRoomType = roomTypeDetails
    }

    override fun onBackPressed() {
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Please Confirm")
            .setMessage("Are you sure you don't want to save the room?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("No", null)
            .create().show()
    }

    private fun validateRoomName(name: String): Pair<Boolean, Int> {
        if(name.isEmpty()) {
            return Pair(false, 0)
        }

        if(name.length < 3) {
            return Pair(false, 1)
        }

        val foundRoom = DeviceDataSource.rooms!!.find {
                r -> r.name == name && (room == null || r.id != room!!.id)
        }

        if(foundRoom != null)
            return Pair(false, 2)

        return Pair(true, -1)
    }
}