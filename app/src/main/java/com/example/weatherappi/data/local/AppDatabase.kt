package com.example.weatherappi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherappi.data.model.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}