package com.example.weatherappi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappi.BuildConfig
import com.example.weatherappi.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

data class WeatherUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val temperatureText: String? = null,
    val description: String? = null,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun updateCity(newCity: String) {
        _uiState.update { it.copy(city = newCity) }
    }

    fun fetchWeather() {
        val city = _uiState.value.city.trim()

        if (city.isEmpty()) {
            _uiState.update { it.copy(error = "Please enter a city name.") }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    temperatureText = null,
                    description = null
                )
            }

            try {
                val result = RetrofitInstance.api.getWeatherByCity(
                    city = city,
                    apiKey = BuildConfig.OPENWEATHER_API_KEY
                )

                val tempRounded = result.main.temp.roundToInt()
                val desc = result.weather.firstOrNull()?.description ?: "No description"

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        temperatureText = "$tempRounded °C",
                        description = desc,
                        error = null
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(isLoading = false, error = "Network error. Check your internet.") }
            } catch (e: HttpException) {
                val msg = if (e.code() == 404) "City not found." else "Server error (${e.code()})."
                _uiState.update { it.copy(isLoading = false, error = msg) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Something went wrong.") }
            }
        }
    }
}
