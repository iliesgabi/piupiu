package com.piu.solar_energy_usage.device

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R

class DeviceAdapter(
    private val context: Activity
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private val dataSource = mutableListOf<Device>()

    override fun getItemViewType(position: Int): Int = R.layout.device_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return DeviceViewHolder(elementView)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = dataSource.size

    fun setDataSource(devices: List<Device>) {
        dataSource.clear()
        dataSource.addAll(devices)
        this.notifyDataSetChanged()
    }

    fun getDevice(position: Int): Device {
        return dataSource[position]
    }

    fun deleteDevice(position: Int) {
        dataSource.removeAt(position)
        this.notifyItemRemoved(position)
    }

    inner class DeviceViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {

        private var deviceIcon: ImageView
        private var deviceName: TextView
        private var deviceStatus: TextView
        private var deviceSwitch: SwitchCompat

        init {
            itemView.setOnCreateContextMenuListener(this)
            deviceIcon = itemView.findViewById(R.id.deviceIcon)
            deviceName = itemView.findViewById(R.id.deviceName)
            deviceStatus = itemView.findViewById(R.id.deviceStatus)
            deviceSwitch = itemView.findViewById(R.id.deviceSwitch)

            addSwitchListener()
        }

        fun bindData(item: Device) {
            deviceName.text = item.name

            setStyle(item.isActive)

            val deviceTypeDetails = DevicesTypes.getDeviceTypeDetails(item.type)
            deviceIcon.setImageResource(deviceTypeDetails.icon)
        }

        private fun addSwitchListener() {
            deviceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                dataSource[adapterPosition].isActive = isChecked
                setStyle(isChecked)
            }
        }

        private fun setStyle(isActive: Boolean) {
            if(isActive) {
                deviceStatus.text = "Device is turned on"
                deviceStatus.setTextColor(Color.parseColor("#3E8E7E"))
                deviceStatus.setTypeface(null, Typeface.BOLD)
                deviceSwitch.isChecked = true
            } else {
                deviceStatus.text = "Device is turned off"
                deviceStatus.setTextColor(Color.parseColor("#383739"))
                deviceStatus.setTypeface(null, Typeface.NORMAL)
                deviceSwitch.isChecked = false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(this.adapterPosition, R.id.editDevice, Menu.NONE, R.string.edit_device)
            menu?.add(this.adapterPosition, R.id.deleteDevice, Menu.NONE, R.string.delete_device)
        }
    }
}