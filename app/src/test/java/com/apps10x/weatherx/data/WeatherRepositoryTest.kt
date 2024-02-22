package com.apps10x.weatherx.data

import com.apps10x.weatherx.helpers.FORECASTS_LIST_SIZE
import com.apps10x.weatherx.helpers.createMockForecastData
import com.apps10x.weatherx.helpers.createMockWeatherResponse
import com.apps10x.weatherx.network.WeatherApiService
import com.apps10x.weatherx.utils.ApiResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class WeatherRepositoryTest {

    @Mock
    lateinit var weatherApiService: WeatherApiService

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherRepository = WeatherRepository(weatherApiService)
    }

    @Test
    fun `test getWeatherToday success`() {
        runBlocking {
            val city = "New York"
            val mockResponse = Response.success(createMockWeatherResponse(city, 0))
            `when`(weatherApiService.getWeatherToday(city = city)).thenReturn(mockResponse)

            val result = weatherRepository.getWeatherToday(city)

            assert(result is ApiResult.Success)
            val data = (result as ApiResult.Success<WeatherResponse>).data
            assertEquals("New York", data?.city)
            // Add more assertions as needed
        }
    }

    @Test
    fun `test getWeatherForecast success`() {
        runBlocking {
            val city = "New York"
            val mockResponse = Response.success(createMockForecastData(city))
            `when`(weatherApiService.getWeatherForecast(city = city)).thenReturn(mockResponse)

            val result = weatherRepository.getWeatherForecast(city)

            assert(result is ApiResult.Success)
            val data = (result as ApiResult.Success<WeatherForecastResponse>).data
            assertEquals(FORECASTS_LIST_SIZE, data?.list?.size)
        }
    }
}
