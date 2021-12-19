package com.piu.solar_energy_usage.meteo

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WeatherAPI {
    @Headers(contentTypeJson)
    @GET(getWeatherURL)
    fun readWeather(@Query(value="key") key: String, @Query(value="q") q: String, @Query(value="days") days: String, @Query(value="aqi") aqi: String, @Query(value="alerts") alerts: String): Call<JsonObject>

    companion object {
        private val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logger)
        }.build()

        fun create(): WeatherAPI {
            val retrofitInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            return retrofitInstance.create(WeatherAPI::class.java)
        }
    }
}

private const val BASE_URL = "http://api.weatherapi.com/v1/"
private const val getWeatherURL = "forecast.json"

private const val contentTypeJson = "Content-Type: application/json"
private const val authorization = "Authorization"
