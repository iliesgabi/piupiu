package com.piu.solar_energy_usage.meteo

import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.piu.solar_energy_usage.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val weatherAPI = WeatherAPI.create()
    private lateinit var weatherAdapter: WeatherAdapter

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val cityName = fetchLocation()

        val searchCity = findViewById<TextInputEditText>(R.id.searchCityEditText)
        val searchButton = findViewById<ImageView>(R.id.searchButton)

        searchButton.setOnClickListener {
            readWeather(searchCity.text.toString())
        }

        readWeather(cityName)

        val weatherRecycleView = findViewById<RecyclerView>(R.id.weatherRecycleView)

        weatherAdapter = WeatherAdapter(context = this)

        weatherRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = weatherAdapter
        }
    }

    private fun readWeather(cityName: String) {
        val city = findViewById<TextView>(R.id.cityName)
        val temperature = findViewById<TextView>(R.id.temperature)
        val conditionImage = findViewById<ImageView>(R.id.conditionImage)
        val condition = findViewById<TextView>(R.id.condition)

        weatherAPI.readWeather("8d5b15af6f0c4af7bfb213235211812", cityName, "1", "no", "no")
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if (response.isSuccessful) {
                        city.text = response.body()?.getAsJsonObject("location")
                            ?.getAsJsonPrimitive("name")?.asString
                        val tempC = response.body()?.getAsJsonObject("current")
                            ?.getAsJsonPrimitive("temp_c").toString() + " °C"
                        temperature.text = tempC
                        val imageSrc = response.body()?.getAsJsonObject("current")
                            ?.getAsJsonObject("condition")?.getAsJsonPrimitive("icon")?.asString
                        println(imageSrc)
                        Picasso.with(this@WeatherActivity)
                            .load("http:" + imageSrc)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(conditionImage)
                        condition.text = response.body()?.getAsJsonObject("current")
                            ?.getAsJsonObject("condition")?.getAsJsonPrimitive("text")?.asString

                        val forecastday = response.body()?.getAsJsonObject("forecast")
                            ?.getAsJsonArray("forecastday")
                            ?.get(0) as JsonObject
                        val list = forecastday.getAsJsonArray("hour")
                        val weatherList = mutableListOf<Weather>()

                        var contor = 0;
                        for (item in list) {
                            val it = item as JsonObject

                            var t = ""
                            if (contor < 10) {
                                t += "0$contor:00"
                            } else {
                                t += "$contor:00"
                            }
                            contor++;

                            val weather = it.getAsJsonObject("condition")
                                ?.getAsJsonPrimitive("icon")?.asString?.let { it1 ->
                                    Weather(
                                        t,
                                        it.getAsJsonPrimitive("temp_c").asDouble,
                                        it1,
                                        it.getAsJsonPrimitive("wind_kph").asInt
                                    )
                                }

                            if (weather != null) {
                                weatherList.add(weather)
                            }
                        }
                        weatherAdapter.addWeathers(weatherList)
                    } else {
                        println("error")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println(t.cause)
                }
            })
    }

    private fun fetchLocation(): String {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        var latitude = 0.0
        var longitude = 0.0
        task.addOnSuccessListener {
            println("!! " + it.latitude + " " + it.longitude)
            latitude = it.latitude
            longitude = it.longitude
            //cityName = "Cluj-Napoca"
        }
        val cityName = getCityName(latitude, longitude)

        return "Cluj-Napoca"
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        var cityName = "Cluj-Napoca"
        val geocoder = Geocoder(baseContext, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(latitude, longitude, 1)

            if (list[0].locality != null)
                if (!list[0].locality.equals(""))
                    cityName = list[0].locality
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return cityName
    }
}