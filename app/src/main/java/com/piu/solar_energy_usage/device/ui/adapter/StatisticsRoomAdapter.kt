package com.piu.solar_energy_usage.device.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.Room
import com.piu.solar_energy_usage.device.data.RoomTypes

class StatisticsRoomAdapter : RecyclerView.Adapter<StatisticsRoomAdapter.StatisticsRoomHolder>() {

    private val dataSource = mutableListOf<Room>()

    override fun getItemViewType(position: Int): Int = R.layout.statistics_room_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsRoomHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return StatisticsRoomHolder(elementView)
    }

    override fun onBindViewHolder(holder: StatisticsRoomHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = dataSource.size

    fun setDataSource(rooms: List<Room>) {
        dataSource.clear()
        dataSource.addAll(rooms)
        this.notifyDataSetChanged()
    }

    inner class StatisticsRoomHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private var roomName: TextView
        private var roomIcon: ImageView
        private var roomConsumption: TextView

        init {
            roomName = itemView.findViewById(R.id.statisticsRoomName)
            roomIcon = itemView.findViewById(R.id.statisticsRoomIcon)
            roomConsumption = itemView.findViewById(R.id.statisticsRoomConsumption)
        }

        fun bindData(item: Room) {
            roomName.text = item.name

            val roomTypeDetails = RoomTypes.getRoomTypeDetails(item.type)
            roomIcon.setImageResource(roomTypeDetails.icon)

            roomConsumption.text = "${(5..20).random().toFloat()} kWh"
        }
    }
}