package com.piu.solar_energy_usage.device.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.data.DevicesTypes
import com.piu.solar_energy_usage.device.ui.device_details.DeviceDetailsActivity

class DeviceAdapter(
    private val context: Activity
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private val dataSource = mutableListOf<Device>()
    private var activeDevices: Int = 0

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
        activeDevices = 0
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
        View.OnCreateContextMenuListener,
        View.OnClickListener {

        private var deviceIconView: CardView
        private var deviceIcon: ImageView
        private var deviceName: TextView
        private var deviceStatus: TextView
        private var deviceSwitch: SwitchCompat

        init {
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)

            deviceIconView = itemView.findViewById(R.id.deviceIconView)
            deviceIcon = itemView.findViewById(R.id.deviceIcon)
            deviceName = itemView.findViewById(R.id.deviceName)
            deviceStatus = itemView.findViewById(R.id.deviceStatus)
            deviceSwitch = itemView.findViewById(R.id.deviceSwitch)
        }

        fun bindData(item: Device) {
            deviceName.text = item.name

            if(item.isActive) {
                activeDevices++
            }

            changeSwitchState(item.isActive)
            addSwitchListener()

            val deviceTypeDetails = DevicesTypes.getDeviceTypeDetails(item.type)
            deviceIcon.setImageResource(deviceTypeDetails.icon)
        }

        private fun addSwitchListener() {
            deviceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(buttonView.isPressed) {
                    dataSource[adapterPosition].isActive = isChecked

                    if(isChecked)
                        activeDevices++
                    else
                        activeDevices--

                    changeSwitchState(isChecked)
                }
            }
        }

        private fun changeSwitchState(isActive: Boolean) {
            if(isActive) {
                deviceStatus.text = "Device is turned on"
                deviceSwitch.isChecked = true

                deviceStatus.setTextColor(Color.parseColor("#3E8E7E"))
                deviceStatus.setTypeface(null, Typeface.BOLD)

                deviceIconView.setCardBackgroundColor(Color.parseColor("#D3E4CD"))
            } else {
                deviceStatus.text = "Device is turned off"
                deviceSwitch.isChecked = false

                deviceStatus.setTextColor(Color.parseColor("#383739"))
                deviceStatus.setTypeface(null, Typeface.NORMAL)

                deviceIconView.setCardBackgroundColor(Color.WHITE)
            }

            val noActiveDevices = context.findViewById<TextView>(R.id.noActiveDevices)
            noActiveDevices.text = "$activeDevices active devices"
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(this.adapterPosition, R.id.editDevice, Menu.NONE, R.string.edit_device)
            menu?.add(this.adapterPosition, R.id.deleteDevice, Menu.NONE, R.string.delete_device)
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra("device", dataSource[adapterPosition])
            context.startActivityForResult(intent, DeviceDetailsActivity.ACTIVITY_ID)
        }
    }
}