package com.piu.solar_energy_usage.device

import java.io.Serializable

data class DeviceType (
    val name: String,
    val icon: Int
) : Serializable {

    override fun toString(): String {
        return name
    }
}