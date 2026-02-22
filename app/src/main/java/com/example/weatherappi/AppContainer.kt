package com.example.weatherappi

import android.content.Context
import androidx.room.Room
import com.example.weatherappi.data.local.AppDatabase
import com.example.weatherappi.data.remote.RetrofitInstance
import com.example.weatherappi.data.repository.WeatherRepository

object AppContainer {

    @Volatile
    private var db: AppDatabase? = null

    fun weatherRepository(context: Context): WeatherRepository {
        val database = db ?: synchronized(this) {
            db ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build().also { db = it }
        }

        return WeatherRepository(
            dao = database.weatherDao(),
            api = RetrofitInstance.api
        )
    }
}