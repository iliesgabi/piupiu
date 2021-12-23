package com.piu.solar_energy_usage.device

import com.piu.solar_energy_usage.R

object DevicesTypes {

    var types: MutableList<DeviceType> = mutableListOf<DeviceType>()

    init {
        types.add(DeviceType(name = "Light", icon = R.drawable.ic_light))
        types.add(DeviceType(name = "Socket", icon = R.drawable.ic_socket))
        types.add(DeviceType(name = "TV", icon = R.drawable.ic_television))
        types.add(DeviceType(name = "Fridge", icon = R.drawable.ic_fridge))
        types.add(DeviceType(name = "Oven", icon = R.drawable.ic_oven))
        types.add(DeviceType(name = "Washing machine", icon = R.drawable.ic_washing_machine))
        types.add(DeviceType(name = "Dryer machine", icon = R.drawable.ic_dryer_machine))
        types.add(DeviceType(name = "Air conditioner", icon = R.drawable.ic_air_conditioner))
        types.add(DeviceType(name = "Others", icon = R.drawable.ic_other_devices))
    }

    fun getDeviceTypeDetails(deviceType: String): DeviceType {

        for(type in types) {
            if(type.name == deviceType)
                return type
        }

        return types[types.size - 1]
    }
}