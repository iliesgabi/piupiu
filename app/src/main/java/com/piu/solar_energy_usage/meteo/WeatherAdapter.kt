package com.piu.solar_energy_usage.meteo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.squareup.picasso.Picasso

class WeatherAdapter(
    private val context: Context
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val dataSource = mutableListOf<Weather>()
    private var design = 1

    inner class WeatherViewHolder(
        itemView: View,
        design: Int
    ) : RecyclerView.ViewHolder(itemView) {

        private var weatherHour: TextView
        private var weatherTemperature: TextView
        private var weatherImage: ImageView
        private var weatherWindSpeed: TextView

        init {
            weatherHour = itemView.findViewById(R.id.cardTime)
            weatherTemperature = itemView.findViewById(R.id.cardTemperature)
            weatherImage = itemView.findViewById(R.id.cardCondition)
            weatherWindSpeed = itemView.findViewById(R.id.cardWindSpeed)
        }

        fun bindData(item: Weather) {
            weatherHour.text = item.time.toString()
            val temp = item.temperature.toString() + " °C"
            weatherTemperature.text = temp
            weatherWindSpeed.text = item.windSpeed.toString()
            Picasso.with(context)
                .load("http:" + item.image)
                .placeholder(R.mipmap.ic_launcher)
                .into(weatherImage)
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.weather_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return WeatherViewHolder(elementView, viewType)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
        holder.itemView.setOnClickListener {
            println(item)
        }
    }

    override fun getItemCount(): Int = dataSource.size

    fun addWeather(weather: Weather) {
        dataSource.add(weather)
        notifyDataSetChanged()
    }

    fun addWeathers(weather: List<Weather>) {
        dataSource.clear()
        dataSource.addAll(weather)
        notifyDataSetChanged()
    }


}


