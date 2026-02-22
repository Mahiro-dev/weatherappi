package com.example.weatherappi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey
    val city: String,
    val temperature: Double,
    val description: String,
    val fetchedAtMillis: Long
)