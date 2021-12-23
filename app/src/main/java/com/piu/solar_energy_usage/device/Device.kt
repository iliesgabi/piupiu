package com.piu.solar_energy_usage.device

import android.content.Context
import org.json.JSONObject
import java.io.Serializable
import java.util.*

data class Device (
    val id: UUID,
    var name: String,
    var roomId: UUID,
    var type: String,
    var isActive: Boolean = false
) : Serializable {

    companion object {

        fun getDevicesFromFile(
            fileName: String,
            context: Context
        ) : List<Device> {

            val devicesList = ArrayList<Device>()

            try {
                val jsonString = loadJsonFromFile(fileName, context)
                val json = JSONObject(jsonString)
                val devices = json.getJSONArray("devices")

                (0 until devices.length())
                    .mapTo(devicesList) { index ->
                        Device(
                            id = UUID.fromString(devices.getJSONObject(index).getString("id")),
                            name = devices.getJSONObject(index).getString("name"),
                            roomId = UUID.fromString(devices.getJSONObject(index).getString("roomId")),
                            type = devices.getJSONObject(index).getString("type"),
                            isActive = devices.getJSONObject(index).getBoolean("isActive")
                        )
                    }

            } catch(e: Exception) {
                e.printStackTrace()
            }

            return devicesList
        }

        private fun loadJsonFromFile(
            fileName: String,
            context: Context
        ) : String {

            var json = ""

            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)

            } catch(e: Exception) {
                e.printStackTrace()
            }

            return json
        }
    }
}