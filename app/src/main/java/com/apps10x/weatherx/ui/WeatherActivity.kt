package com.apps10x.weatherx.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps10x.weatherx.R
import com.apps10x.weatherx.databinding.ActivityWeatherBinding
import com.apps10x.weatherx.ui.adapters.WeatherForecastAdapter
import com.apps10x.weatherx.utils.ApiResult
import com.apps10x.weatherx.utils.hide
import com.apps10x.weatherx.utils.setTextAndShow
import com.apps10x.weatherx.utils.show
import com.apps10x.weatherx.utils.showIndefiniteSnackBarWithAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private var _binding: ActivityWeatherBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()

    companion object {
        private const val CITY = "Bengaluru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setObservers()
        getWeatherData()
    }

    private fun getWeatherData() {
        binding.loader.show()
        weatherViewModel.getWeatherToday(CITY)
        weatherViewModel.getWeatherForecast(CITY)
    }

    private fun setObservers() {
        weatherViewModel.weatherTodayResponse.observe(this) {
            binding.loader.hide()
            when (it) {
                is ApiResult.Success -> {
                    binding.tvTemperature.setTextAndShow(
                        getString(
                            R.string.temperature_celsius,
                            it.data?.temperatureData?.tempCelsius?.toInt()
                        )
                    )
                    binding.tvCity.setTextAndShow(it.data?.city)
                }

                is ApiResult.Error -> {
                    binding.root.showIndefiniteSnackBarWithAction(
                        it.error ?: getString(R.string.something_went_wrong),
                        getString(R.string.retry),
                    ) {
                        weatherViewModel.getWeatherToday(CITY)
                    }
                }
            }
        }

        weatherViewModel.dailyAverageForecasts.observe(this) {
            binding.loader.hide()
            when (it) {
                is ApiResult.Success -> {
                    it.data?.let { forecasts ->
                        binding.rvForecasts.apply {
                            setHasFixedSize(true)
                            adapter = WeatherForecastAdapter(forecasts)
                            layoutManager = LinearLayoutManager(context)
                            show()
                        }
                    }
                }

                is ApiResult.Error -> {
                    binding.root.showIndefiniteSnackBarWithAction(
                        it.error ?: getString(R.string.something_went_wrong),
                        getString(R.string.retry),
                    ) {
                        weatherViewModel.getWeatherForecast(CITY)
                    }
                }

                null -> {
                    binding.root.showIndefiniteSnackBarWithAction(
                        getString(R.string.something_went_wrong),
                        getString(R.string.retry),
                    ) {
                        weatherViewModel.getWeatherForecast(CITY)
                    }
                }
            }
        }
    }
}