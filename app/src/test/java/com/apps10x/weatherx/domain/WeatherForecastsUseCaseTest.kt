package com.apps10x.weatherx.domain

import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.data.WeatherForecastResponse
import com.apps10x.weatherx.data.WeatherRepository
import com.apps10x.weatherx.helpers.createMockForecastData
import com.apps10x.weatherx.utils.ApiResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class WeatherForecastsUseCaseTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository

    private lateinit var weatherForecastsUseCase: WeatherForecastsUseCase

    companion object {
        private const val FORECAST_COUNT = 4L
        private const val NO_DATA_ERROR = "No data found"
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherForecastsUseCase = WeatherForecastsUseCase(weatherRepository)
    }

    @Test
    fun `test successful retrieval of weather forecasts`() {
        runBlocking {
            val city = "New York"
            val mockForecastData = createMockForecastData(city)
            `when`(weatherRepository.getWeatherForecast(city)).thenReturn(
                ApiResult.Success(mockForecastData)
            )

            val result = weatherForecastsUseCase(city)

            assert(result is ApiResult.Success)
            val data = (result as ApiResult.Success<List<DailyWeatherForecast>>).data
            // Ensure the correct number of daily weather forecasts is returned
            assertEquals(4, data?.size)

            val currentDate = LocalDate.now()
            // Ensure averages are correct
            mockForecastData.list.filter {
                it.date?.isAfter(currentDate) == true &&
                        (it.date ?: currentDate.plusDays(
                            FORECAST_COUNT + 1
                        )) <= currentDate.plusDays(FORECAST_COUNT)
            }.groupBy { it.date }.values.forEachIndexed { i, it ->
                assertEquals(
                    it.map { it.temperatureData.tempCelsius }.average(),
                    data?.get(i)?.avgTemp
                )
            }
        }
    }

    @Test
    fun `test no data error handling`() {
        runBlocking {
            val city = "Unknown City"
            `when`(weatherRepository.getWeatherForecast(city)).thenReturn(
                ApiResult.Success(WeatherForecastResponse(listOf()))
            )

            val result = weatherForecastsUseCase(city)

            assert(result is ApiResult.Error)
            assertEquals(NO_DATA_ERROR, (result as ApiResult.Error).error)
        }
    }

    @Test
    fun `test error handling`() {
        runBlocking {
            val city = "Unknown City"
            `when`(weatherRepository.getWeatherForecast(city)).thenReturn(ApiResult.Error("error message"))

            val result = weatherForecastsUseCase(city)

            assert(result is ApiResult.Error)
            assertEquals("error message", (result as ApiResult.Error).error)
        }
    }
}

