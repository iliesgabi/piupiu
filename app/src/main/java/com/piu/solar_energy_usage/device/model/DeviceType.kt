package com.piu.solar_energy_usage.device.model

import java.io.Serializable

data class DeviceType (
    val name: String,
    val icon: Int
) : Serializable {

    override fun toString(): String {
        return name
    }
}