package com.piu.solar_energy_usage.device.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Room
import com.piu.solar_energy_usage.device.data.RoomTypes

class AutoCompleteRoomAdapter (
    context: Activity,
    private val list: MutableList<Room>
) : ArrayAdapter<Room>(context, 0, list) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = inflater.inflate(R.layout.type_item, parent, false)

        val roomTypeIcon = itemView.findViewById<ImageView>(R.id.typeIconItem)
        val roomName = itemView.findViewById<TextView>(R.id.typeNameItem)
        val room = list[position]

        roomName.text = room.name
        val roomTypeDetails = RoomTypes.getRoomTypeDetails(room.type)
        roomTypeIcon.setImageResource(roomTypeDetails.icon)

        return itemView
    }
}