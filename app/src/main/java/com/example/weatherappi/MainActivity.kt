package com.example.weatherappi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.weatherappi.ui.theme.WeatherScreen
import com.example.weatherappi.ui.theme.WeatherappiTheme
import com.example.weatherappi.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = AppContainer.weatherRepository(this)
        val factory = WeatherViewModelFactory(repo)

        enableEdgeToEdge()
        setContent {
            WeatherappiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(
                        modifier = Modifier.padding(innerPadding),
                        factory = factory
                    )
                }
            }
        }
    }
}