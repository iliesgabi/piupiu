package com.piu.solar_energy_usage.solar_panels.model

import java.util.*

data class SolarPanel (
    val id: Int,
    var title: String,
    var description: String,
    var largeDescriptions: String,
    var warrantyLeft: Int,
    var energyGained: Int,
        )