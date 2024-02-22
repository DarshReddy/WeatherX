package com.apps10x.weatherx.domain

import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.data.WeatherRepository
import com.apps10x.weatherx.utils.ApiResult
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class WeatherForecastsUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    companion object {
        private const val FORECAST_COUNT = 4L
        private const val NO_DATA_ERROR = "No data found"
    }

    suspend operator fun invoke(city: String): ApiResult<List<DailyWeatherForecast>>? {
        val listDailyWeatherForecast = mutableListOf<DailyWeatherForecast>()

        val forecasts = weatherRepository.getWeatherForecast(city)

        return if (forecasts is ApiResult.Success) {
            val currentDate = LocalDate.now()
            forecasts.data?.list?.filter {
                it.date?.isAfter(currentDate) == true &&
                        it.date <= currentDate.plusDays(FORECAST_COUNT)
            }?.groupBy { it.date }?.values?.forEach { weatherResponses ->
                listDailyWeatherForecast.add(
                    DailyWeatherForecast(
                        weatherResponses.map { it.temperatureData.tempCelsius }.average(),
                        weatherResponses.firstOrNull()?.date?.dayOfWeek?.getDisplayName(
                            TextStyle.FULL,
                            Locale.getDefault()
                        ) ?: "-"
                    )
                )
            }
            if (listDailyWeatherForecast.size > 0) {
                ApiResult.Success(data = listDailyWeatherForecast)
            } else {
                ApiResult.Error(NO_DATA_ERROR)
            }
        } else {
            forecasts as? ApiResult.Error
        }
    }
}