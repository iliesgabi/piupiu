package com.piu.solar_energy_usage.provider

import com.piu.solar_energy_usage.R

object Util {

    fun getProviderIcon(name: String): Int = when (name) {
        "Aurora Solar" -> R.drawable.ic_aurora_solar
        "Sunrun" -> R.drawable.ic_sunrun
        "8minute Solar Energy" -> R.drawable.ic_8minute_solar_energy
        "Sunpro Solar" -> R.drawable.ic_sunpro_solar
        else -> -1
    }

}