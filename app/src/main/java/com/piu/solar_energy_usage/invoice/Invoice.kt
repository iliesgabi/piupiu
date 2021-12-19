package com.piu.solar_energy_usage.invoice

import android.content.Context
import org.json.JSONObject
import java.io.Serializable

data class Invoice (
    val number: String,
    val price: Double,
    var isPaid: Boolean = false
): Serializable {

    companion object {
        fun getInvoicesFromFile(
            fileName: String,
            context: Context
        ): List<Invoice> {

            val invoicesList = ArrayList<Invoice>()

            try {
                val jsonString = loadJsonFromFile(fileName, context)
                val json = JSONObject(jsonString)
                val invoices = json.getJSONArray("invoices")

                (0 until invoices.length())
                    .mapTo(invoicesList) { index ->
                        Invoice(
                            number = invoices.getJSONObject(index).getString("number"),
                            price = invoices.getJSONObject(index).getDouble("price")
                        )
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return invoicesList
        }

        private fun loadJsonFromFile(
            fileName: String,
            context: Context
        ): String {
            var json = ""
            try {
                val inputStream = context.assets.open(fileName)
                var size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

            return json
        }

    }
}