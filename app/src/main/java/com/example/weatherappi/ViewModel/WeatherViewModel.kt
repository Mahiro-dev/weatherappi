package com.example.weatherappi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappi.data.model.WeatherEntity
import com.example.weatherappi.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val cityFlow = MutableStateFlow("")

    val cachedWeather: StateFlow<WeatherEntity?> = cityFlow
        .flatMapLatest { city -> repository.observeCity(city.trim()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updateCity(newCity: String) {
        _uiState.update { it.copy(city = newCity, error = null) }
    }

    fun fetchWeather() {
        val city = _uiState.value.city.trim()
        if (city.isEmpty()) {
            _uiState.update { it.copy(error = "Please enter a city name.") }
            return
        }

        // Start observing this city in Room
        cityFlow.value = city

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.refreshIfStale(city)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "Failed to fetch weather.") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}