package com.piu.solar_energy_usage.device

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.piu.solar_energy_usage.R

class AutoCompleteDeviceTypeAdapter(
    private val context: Activity,
    private val list: MutableList<DeviceType>
) : ArrayAdapter<DeviceType>(context, 0, list) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = inflater.inflate(R.layout.type_item, parent, false)

        val deviceTypeIcon = itemView.findViewById<ImageView>(R.id.typeIconItem)
        val deviceTypeName = itemView.findViewById<TextView>(R.id.typeNameItem)
        val deviceType = list[position]

        deviceTypeName.text = deviceType.name
        deviceTypeIcon.setImageResource(deviceType.icon)

        return itemView
    }
}