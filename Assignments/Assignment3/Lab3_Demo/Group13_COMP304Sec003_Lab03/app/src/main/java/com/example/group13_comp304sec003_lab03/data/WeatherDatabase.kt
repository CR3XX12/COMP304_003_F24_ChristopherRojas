package com.example.group13_comp304sec003_lab03.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [WeatherEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to =2)
    ]
)
@TypeConverters(WeathersTypeConverters::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
