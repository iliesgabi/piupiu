package com.piu.solar_energy_usage.provider.model

import android.content.Context
import org.json.JSONObject
import java.io.Serializable

data class Provider(
    var id: String,
    var name: String,
    var contact: String,
    var description: String,
    var rating: Double
) : Serializable {

    companion object {

        fun getProvidersFromFile(
            fileName: String,
            context: Context
        ): List<Provider> {

            val providersList = ArrayList<Provider>()

            try {
                val jsonString = loadJsonFromFile(fileName, context)
                val json = JSONObject(jsonString)
                val providers = json.getJSONArray("providers")

                (0 until providers.length())
                    .mapTo(providersList) { index ->
                        Provider(
                            id = providers.getJSONObject(index).getString("id"),
                            name = providers.getJSONObject(index).getString("name"),
                            contact = providers.getJSONObject(index).getString("contact"),
                            description = providers.getJSONObject(index).getString("description"),
                            rating = providers.getJSONObject(index).getDouble("rating")
                        )
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return providersList
        }


        private fun loadJsonFromFile(
            fileName: String,
            context: Context
        ): String {

            var json = ""

            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return json
        }

    }
}
