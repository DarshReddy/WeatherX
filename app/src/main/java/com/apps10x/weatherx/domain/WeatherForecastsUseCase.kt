package com.apps10x.weatherx.domain

import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.data.WeatherRepository
import com.apps10x.weatherx.utils.ApiResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class WeatherForecastsUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    companion object {
        private const val DAYS_TO_SHOW = 4L
        private const val NO_DATA_ERROR = "No data found"
        private const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }

    suspend operator fun invoke(city: String): ApiResult<List<DailyWeatherForecast>>? {
        val listDailyWeatherForecast = mutableListOf<DailyWeatherForecast>()

        val forecasts = weatherRepository.getWeatherForecast(city)

        return if (forecasts is ApiResult.Success) {
            forecasts.data?.list?.let {
                val currentDate = LocalDate.now()
                val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
                val dailyAverages = mutableMapOf<LocalDate, Pair<Double, Int>>()

                for (forecast in it) {
                    val forecastDate =
                        LocalDate.parse(forecast.dateText, dateFormatter)

                    if (
                        forecastDate.isAfter(currentDate) &&
                        forecastDate <= currentDate.plusDays(DAYS_TO_SHOW)
                    ) {
                        val temperature = forecast.main.tempCelsius
                        val (sum, count) = dailyAverages.getOrDefault(forecastDate, Pair(0.0, 0))
                        dailyAverages[forecastDate] = Pair(sum + temperature, count + 1)
                    }
                }

                for ((date, pair) in dailyAverages) {
                    val avgTemp = pair.first / pair.second
                    val dayOfWeek = date.dayOfWeek.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    )
                    listDailyWeatherForecast.add(DailyWeatherForecast(avgTemp, dayOfWeek))
                }

                ApiResult.Success(data = listDailyWeatherForecast)
            } ?: run {
                ApiResult.Error(null, error = NO_DATA_ERROR)
            }
        } else {
            forecasts as? ApiResult.Error
        }
    }
}