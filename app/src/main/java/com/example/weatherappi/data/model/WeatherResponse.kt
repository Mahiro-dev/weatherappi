package com.example.weatherappi.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<WeatherDescription>
)

data class Main(
    @SerializedName("temp")
    val temp: Double
)

data class WeatherDescription(
    @SerializedName("description")
    val description: String
)
