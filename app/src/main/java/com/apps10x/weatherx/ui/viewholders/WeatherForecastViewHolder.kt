package com.apps10x.weatherx.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.apps10x.weatherx.R
import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.databinding.ItemWeatherForecastBinding
import com.apps10x.weatherx.utils.setTextAndShow

class WeatherForecastViewHolder(
    private val binding: ItemWeatherForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dailyWeatherForecast: DailyWeatherForecast?) {
        dailyWeatherForecast?.let {
            binding.tvDay.setTextAndShow(it.dayOfWeek)
            binding.tvTemperature.setTextAndShow(
                itemView.context.getString(
                    R.string.temperature_celsius,
                    it.avgTemp.toInt()
                )
            )
        }
    }
}