package com.apps10x.weatherx.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.databinding.ItemWeatherForecastBinding
import com.apps10x.weatherx.ui.viewholders.WeatherForecastViewHolder

class WeatherForecastAdapter(
    private val aysAvgWeatherList: List<DailyWeatherForecast>,
) : RecyclerView.Adapter<WeatherForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder =
        WeatherForecastViewHolder(
            ItemWeatherForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = aysAvgWeatherList.size

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.bind(aysAvgWeatherList[position])
    }
}