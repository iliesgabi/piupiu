package com.piu.solar_energy_usage.device.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.data.DevicesTypes
import com.piu.solar_energy_usage.device.model.Device
import com.piu.solar_energy_usage.device.ui.device_details.DeviceDetailsActivity

class ActiveDeviceAdapter(
    private val context: Activity
) : RecyclerView.Adapter<ActiveDeviceAdapter.ActiveDeviceViewHolder>() {

    private val dataSource = mutableListOf<Device>()
    private var activeDevices: Int = 0

    override fun getItemViewType(position: Int): Int = R.layout.device_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveDeviceViewHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return ActiveDeviceViewHolder(elementView)
    }

    override fun onBindViewHolder(holder: ActiveDeviceViewHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = dataSource.size

    fun setDataSource(devices: List<Device>) {
        dataSource.clear()

        if(devices.isNullOrEmpty()) {
            val noActiveDevice = context.findViewById<TextView>(R.id.noActiveDevicesMessage)
            noActiveDevice.visibility = View.VISIBLE
            notifyActiveDevicesChange(0)
            return
        }

        dataSource.addAll(devices)

        activeDevices = devices.size
        notifyActiveDevicesChange(activeDevices)
    }

    fun notifyDeviceChange(device: Device) {
        for((index, item) in dataSource.withIndex()) {
            if(item.id == device.id) {
                if(!device.isActive) {
                    deleteDevice(index)
                    notifyActiveDevicesChange(--activeDevices)
                    return
                }

                item.name = device.name
                item.type = device.type
                item.roomId = device.roomId

                notifyItemChanged(index)
                return
            }
        }
    }

    fun deleteDevice(position: Int) {
        dataSource.removeAt(position)
        this.notifyItemRemoved(position)

        if(dataSource.isNullOrEmpty()) {
            val noActiveDevice = context.findViewById<TextView>(R.id.noActiveDevicesMessage)
            noActiveDevice.visibility = View.VISIBLE
        }
    }

    private fun notifyActiveDevicesChange(activeDevices: Int) {
        val noActiveDevices = context.findViewById<TextView>(R.id.noActiveDevices2)
        noActiveDevices.text = "$activeDevices active devices"
    }

    inner class ActiveDeviceViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var deviceIconView: CardView
        private var deviceIcon: ImageView
        private var deviceName: TextView
        private var deviceStatus: TextView
        private var deviceSwitch: SwitchCompat

        init {
            itemView.setOnClickListener(this)

            deviceIconView = itemView.findViewById(R.id.deviceIconView)
            deviceIcon = itemView.findViewById(R.id.deviceIcon)
            deviceName = itemView.findViewById(R.id.deviceName)
            deviceStatus = itemView.findViewById(R.id.deviceStatus)
            deviceSwitch = itemView.findViewById(R.id.deviceSwitch)
        }

        fun bindData(item: Device) {
            deviceName.text = item.name

            activateSwitch()
            addSwitchListener()

            val deviceTypeDetails = DevicesTypes.getDeviceTypeDetails(item.type)
            deviceIcon.setImageResource(deviceTypeDetails.icon)
        }

        private fun addSwitchListener() {
            deviceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(buttonView.isPressed) {
                    dataSource[adapterPosition].isActive = isChecked

                    if(!isChecked) {
                        notifyActiveDevicesChange(--activeDevices)

                        deleteDevice(adapterPosition)

                        Toast.makeText(
                            context,
                            "Device is turned off",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        private fun activateSwitch() {
            deviceStatus.text = "Device is turned on"
            deviceSwitch.isChecked = true

            deviceStatus.setTextColor(Color.parseColor("#3E8E7E"))
            deviceStatus.setTypeface(null, Typeface.BOLD)

            deviceIconView.setCardBackgroundColor(Color.parseColor("#D3E4CD"))
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra("device", dataSource[adapterPosition])
            context.startActivityForResult(intent, DeviceDetailsActivity.ACTIVITY_ID)
        }
    }
}