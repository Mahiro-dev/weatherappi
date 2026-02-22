package com.example.weatherappi.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappi.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    factory: ViewModelProvider.Factory
) {
    val weatherViewModel: WeatherViewModel = viewModel(factory = factory)

    val uiState by weatherViewModel.uiState.collectAsState()
    val cached = weatherViewModel.cachedWeather.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.city,
            onValueChange = { weatherViewModel.updateCity(it) },
            label = { Text("City") },
            singleLine = true,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = { weatherViewModel.fetchWeather() },
            enabled = !uiState.isLoading
        ) {
            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Loading...")
        }

        uiState.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (cached != null) {
            val tempText = "${cached.temperature.roundToInt()} °C"
            Text("Temperature: $tempText")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Description: ${cached.description}")

            // --- Proof of Room + cache: show timestamp and age ---
            val minutesAgo = ((System.currentTimeMillis() - cached.fetchedAtMillis) / 60000L).toInt()
            val timeText = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(Date(cached.fetchedAtMillis))

            Spacer(modifier = Modifier.height(8.dp))
            Text("Room cache: $timeText ($minutesAgo min ago)")
        } else if (!uiState.isLoading && uiState.error == null) {
            Text("No cached weather yet. Enter a city and press Hae sää.")
        }
    }
}