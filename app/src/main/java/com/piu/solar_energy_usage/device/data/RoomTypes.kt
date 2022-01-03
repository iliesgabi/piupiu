package com.piu.solar_energy_usage.device.data

import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.device.model.RoomType

object RoomTypes {

    var types: MutableList<RoomType> = mutableListOf<RoomType>()

    init {
        types.add(RoomType(name = "Kitchen", image = R.drawable.kitchen, icon = R.drawable.ic_kitchen))
        types.add(RoomType(name = "Bathroom", image = R.drawable.bathroom, icon = R.drawable.ic_bath))
        types.add(RoomType(name = "Bedroom", image = R.drawable.bedroom, icon = R.drawable.ic_bedroom))
        types.add(RoomType(name = "Livingroom", image = R.drawable.livingroom, icon = R.drawable.ic_livingroom))
        types.add(RoomType(name = "Garage", image = R.drawable.ic_garage, icon = R.drawable.ic_garage))
        types.add(RoomType(name = "Workspace", image = R.drawable.workspace, icon = R.drawable.ic_workspace))
        types.add(RoomType(name = "Others", image = R.drawable.ic_other_rooms, icon = R.drawable.ic_other_rooms))
    }

    fun getRoomTypeDetails(roomType: String): RoomType {

        for(type in types) {
            if(type.name == roomType)
                return type
        }

        return types[types.size - 1]
    }
}