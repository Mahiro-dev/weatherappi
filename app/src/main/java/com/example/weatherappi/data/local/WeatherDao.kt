package com.example.weatherappi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherappi.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_cache WHERE city = :city LIMIT 1")
    fun observeCity(city: String): Flow<WeatherEntity?>

    @Query("SELECT * FROM weather_cache WHERE city = :city LIMIT 1")
    suspend fun getCityOnce(city: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherEntity)
}