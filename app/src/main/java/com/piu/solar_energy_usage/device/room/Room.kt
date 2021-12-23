package com.piu.solar_energy_usage.device.room

import android.content.Context
import org.json.JSONObject
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Room (
    val id: UUID,
    var name: String,
    var type: String
) : Serializable {

    companion object {

        fun getRoomsFromFile(
            fileName: String,
            context: Context
        ) : List<Room> {

            val roomsList = ArrayList<Room>()

            try {
                val jsonString = loadJsonFromFile(fileName, context)
                val json = JSONObject(jsonString)
                val rooms = json.getJSONArray("rooms")

                (0 until rooms.length())
                    .mapTo(roomsList) { index ->
                       Room(
                            id = UUID.fromString(rooms.getJSONObject(index).getString("id")),
                            name = rooms.getJSONObject(index).getString("name"),
                            type = rooms.getJSONObject(index).getString("type")
                        )
                    }

            } catch(e: Exception) {
                e.printStackTrace()
            }

            return roomsList
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