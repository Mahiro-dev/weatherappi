## Week 5 – Weather App (Retrofit + OpenWeather)

This application fetches weather data from the OpenWeather API and displays it using Jetpack Compose.

## What Retrofit does

Retrofit handles HTTP requests to the OpenWeather API.

- It sends the request to the weather endpoint.

- It receives the JSON response from the server.

- It converts the response into Kotlin data classes using a converter.

## How JSON is converted to data classes

Gson is used as the converter.

- The OpenWeather API returns JSON.

- Gson automatically maps the JSON fields into Kotlin data classes.

- The data classes match the structure of the API response.

This conversion happens automatically inside Retrofit through the GsonConverterFactory.

## How coroutines work in this project

- The API function in the Retrofit interface is defined as a suspend function.

- The request is executed inside viewModelScope.launch in the ViewModel.

- The network call runs in the background.

- When the data is received, the UI updates automatically.

## How UI state works

ViewModel manages a WeatherUiState data class.
UI state contains city, loading state, weather data, and possible errors.

ViewModel updates the state when:

- the user changes the city

- a request starts

- data is received

- an error occurs

- Jetpack Compose observes the state using collectAsState.

- When the state changes, Compose recomposes automatically.

## How the API key is stored

The API key is not hardcoded in the source code.

- It is stored in local.properties.

- It is exposed to the app using buildConfigField.

- It is accessed through BuildConfig.OPENWEATHER_API_KEY.

- The key is passed to Retrofit when making the API request.

## APK 
its provided in the release
