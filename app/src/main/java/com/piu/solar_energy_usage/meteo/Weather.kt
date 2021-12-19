package com.piu.solar_energy_usage.meteo

import java.io.Serializable

data class Weather(
    val time: String,
    val temperature: Double,
    val image: String,
    val windSpeed: Int
): Serializable {
}