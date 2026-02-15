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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val uiState by weatherViewModel.uiState.collectAsState()

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
        } else {
            uiState.error?.let {
                Text(it)
            }

            if (uiState.temperatureText != null && uiState.description != null) {
                Text("Temperature: ${uiState.temperatureText}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Description: ${uiState.description}")
            } else if (uiState.error == null) {
                Text("Enter a city and press Hae sää.")
            }
        }
    }
}
