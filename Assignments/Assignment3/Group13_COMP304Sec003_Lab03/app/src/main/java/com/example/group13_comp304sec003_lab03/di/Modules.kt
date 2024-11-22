package com.example.group13_comp304sec003_lab03.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.group13_comp304sec003_lab03.data.WeatherDatabase
import com.example.group13_comp304sec003_lab03.data.WeathersAPI
import com.example.group13_comp304sec003_lab03.data.WeathersRepository
import com.example.group13_comp304sec003_lab03.data.WeathersRepositoryImpl
import com.example.group13_comp304sec003_lab03.viewmodel.WeathersViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {
    single<WeathersRepository> { WeathersRepositoryImpl(get(), get(), get()) }
    single { Dispatchers.IO }
    single { WeathersViewModel(get()) }
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)) // Kotlinx serialization converter
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
    }
    single { get<Retrofit>().create(WeathersAPI::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather-database"
        ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build()
    }
    single { get<WeatherDatabase>().weatherDao()}
}