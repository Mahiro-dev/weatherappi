package com.example.weatherappi.data.repository

import com.example.weatherappi.BuildConfig
import com.example.weatherappi.data.local.WeatherDao
import com.example.weatherappi.data.model.WeatherEntity
import com.example.weatherappi.data.remote.WeatherApi

class WeatherRepository(
    private val dao: WeatherDao,
    private val api: WeatherApi
) {
    fun observeCity(city: String) = dao.observeCity(city)

    suspend fun refreshIfStale(city: String) {
        val trimmed = city.trim()
        if (trimmed.isEmpty()) return

        val cached = dao.getCityOnce(trimmed)
        val now = System.currentTimeMillis()
        val maxAgeMillis = 30L * 60L * 1000L

        val isFresh = cached != null && (now - cached.fetchedAtMillis) <= maxAgeMillis
        if (isFresh) return

        val result = api.getWeatherByCity(
            city = trimmed,
            apiKey = BuildConfig.OPENWEATHER_API_KEY
        )

        val desc = result.weather.firstOrNull()?.description ?: "No description"

        dao.upsert(
            WeatherEntity(
                city = trimmed,
                temperature = result.main.temp,
                description = desc,
                fetchedAtMillis = now
            )
        )
    }
}