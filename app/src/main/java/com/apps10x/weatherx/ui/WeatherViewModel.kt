package com.apps10x.weatherx.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps10x.weatherx.data.DailyWeatherForecast
import com.apps10x.weatherx.data.WeatherRepository
import com.apps10x.weatherx.data.WeatherResponse
import com.apps10x.weatherx.domain.WeatherForecastsUseCase
import com.apps10x.weatherx.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    private val _weatherTodayResponse = MutableLiveData<ApiResult<WeatherResponse>>()
    val weatherTodayResponse: LiveData<ApiResult<WeatherResponse>> = _weatherTodayResponse
    private val _dailyAverageForecasts =
        MutableLiveData<ApiResult<List<DailyWeatherForecast>>?>()
    val dailyAverageForecasts: LiveData<ApiResult<List<DailyWeatherForecast>>?> =
        _dailyAverageForecasts

    fun getWeatherToday(city: String) {
        viewModelScope.launch {
            _weatherTodayResponse.postValue(repository.getWeatherToday(city))
        }
    }

    fun getWeatherForecast(city: String) = viewModelScope.launch {
        _dailyAverageForecasts.postValue(WeatherForecastsUseCase(repository).invoke(city))
    }
}