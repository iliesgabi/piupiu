package com.piu.solar_energy_usage.device
import com.piu.solar_energy_usage.device.room.Room
import java.util.*
import kotlin.collections.ArrayList

object DeviceDataSource {
    var rooms: MutableList<Room>? = null
    var devices: MutableList<Device>? = null

    fun getDevicesByRoomId(roomId: UUID) : List<Device>? {
        if(devices.isNullOrEmpty())
            return null

        val devicesList = ArrayList<Device>()

        for(device in devices!!) {
            if(device.roomId == roomId)
                devicesList.add(device)
        }

        return devicesList
    }

    fun insertRoom(room: Room) {
        for(r in rooms!!) {
            if(r.id == room.id) {
                r.name = room.name
                r.type = room.type
                return
            }
        }

        rooms!!.add(room)
    }

    fun insertDevice(device: Device) {
        for(d in devices!!) {
            if(d.id == device.id) {
                d.name = device.name
                d.roomId = device.roomId
                d.type = device.type
                return
            }
        }

        devices!!.add(device)
    }
}