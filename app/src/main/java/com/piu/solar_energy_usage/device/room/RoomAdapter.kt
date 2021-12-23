package com.piu.solar_energy_usage.device.room

import android.app.Activity
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.Device
import com.piu.solar_energy_usage.device.DeviceAdapter
import com.piu.solar_energy_usage.device.DeviceDataSource
import com.squareup.picasso.Picasso
import java.util.*

class RoomAdapter(
    private val context: Activity,
    private val deviceAdapter: DeviceAdapter
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    lateinit var currentRoom: Room
    private val dataSource = mutableListOf<Room>()
    private var selectedItem: Int = 0

    override fun getItemViewType(position: Int): Int = R.layout.room_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return RoomViewHolder(elementView)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = dataSource.size

    fun setDataSource(rooms: List<Room>) {
        dataSource.clear()
        dataSource.addAll(rooms)
        this.notifyDataSetChanged()

        if(rooms.isNotEmpty()) {
            selectedItem = 0
            setViewContent(rooms[0])
        } else {
            setViewContent(null)
        }
    }

    fun insertRoom(room: Room) {
        dataSource.add(room)

        val previousItem = selectedItem
        selectedItem = dataSource.size - 1
        notifyItemChanged(previousItem)
        notifyItemInserted(dataSource.size - 1)
        setViewContent(room)
    }

    fun deleteRoom(position: Int) {
        val dataSourceSize = dataSource.size
        if(position == dataSourceSize - 1) {
            if(position - 1 >= 0) {
                val room = dataSource[position - 1]

                if(selectedItem == position) {
                    selectedItem = position - 1
                    notifyItemChanged(selectedItem)
                }
                setViewContent(room)
            } else {
                setViewContent(null)
            }
        } else {
            if(position + 1 < dataSourceSize) {
                val room = dataSource[position + 1]

                notifyItemChanged(selectedItem + 1)
                setViewContent(room)
            } else {
                setViewContent(null)
            }
        }

        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun setViewContent(room: Room?) {
        val roomName = context.findViewById<TextView>(R.id.roomName)
        val roomImage = context.findViewById<ImageView>(R.id.roomImage)

        if(room == null) {
            roomName.text = "No rooms"
            Picasso.with(context)
                .load(R.drawable.no_room)
                .placeholder(R.mipmap.ic_launcher)
                .into(roomImage)

            insertDevices(emptyList())

            val addDeviceButton = context.findViewById<FloatingActionButton>(R.id.addDevice)
            addDeviceButton.visibility = View.GONE
        } else {
            currentRoom = room

            roomName.text = room.name
            val roomTypeDetails = RoomsTypes.getRoomTypeDetails(room.type)
            Picasso.with(context)
                .load(roomTypeDetails.image)
                .placeholder(R.mipmap.ic_launcher)
                .into(roomImage)

            val devices = DeviceDataSource.getDevicesByRoomId(room.id)
            insertDevices(devices)

            val addDeviceButton = context.findViewById<FloatingActionButton>(R.id.addDevice)
            addDeviceButton.visibility = View.VISIBLE
        }
    }

    private fun insertDevices(devices: List<Device>?) {
        if(devices == null)
            return

        deviceAdapter.setDataSource(devices!!)
    }

    fun selectRoom(roomId: UUID) {
        for((index, room) in dataSource.withIndex()) {
            if(room.id == roomId) {
                val previousItem = selectedItem
                selectedItem = index
                notifyItemChanged(previousItem)
                notifyItemChanged(selectedItem)
                setViewContent(room)
                break
            }
        }
    }

    fun notifyRoomChange(position: Int, room: Room) {
        dataSource[position].name = room.name
        dataSource[position].type = room.type

        val previousItem = selectedItem
        selectedItem = position
        notifyItemChanged(previousItem)
        notifyItemChanged(selectedItem)
        setViewContent(dataSource[position])
    }

    inner class RoomViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnCreateContextMenuListener {

        private var roomIcon: ImageView
        private var roomName: TextView
        private var roomCard: CardView

        init {
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)

            roomIcon = itemView.findViewById(R.id.roomIconItem )
            roomName = itemView.findViewById(R.id.roomNameItem)
            roomCard = itemView.findViewById(R.id.roomCard)
        }

        fun bindData(item: Room) {
            val roomTypeDetails = RoomsTypes.getRoomTypeDetails(item.type)
            roomIcon.setImageResource(roomTypeDetails.icon)
            roomName.text = item.name

            if(selectedItem == adapterPosition)
                roomCard.setCardBackgroundColor(context.resources.getColor(R.color.room_highlight_card))
            else
                roomCard.setCardBackgroundColor(context.resources.getColor(R.color.room_card))
        }

        override fun onClick(v: View?) {
            val room = dataSource[adapterPosition]

            roomCard.setCardBackgroundColor(context.resources.getColor(R.color.room_highlight_card))
            val previousItem = selectedItem
            selectedItem = adapterPosition

            notifyItemChanged(previousItem)
            setViewContent(room)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(this.adapterPosition, R.id.editRoom, Menu.NONE, R.string.edit_room)
            menu?.add(this.adapterPosition, R.id.deleteRoom, Menu.NONE, R.string.delete_room)
        }
    }
}