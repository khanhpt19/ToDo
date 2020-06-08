package com.khanhpt.todo.di

import android.content.Context
import androidx.room.Room
import com.khanhpt.todo.data.local.AppDatabase
import com.khanhpt.todo.data.repository.TaskRepository
import com.khanhpt.todo.data.repository.TaskRepositoryImpl
import com.khanhpt.todo.data.repository.WeatherRepository
import com.khanhpt.todo.data.repository.WeatherRepositoryImpl
import org.koin.dsl.module
import org.koin.experimental.builder.create

val repositoryModule = module {
    single { createAppDatabase(get()) }
    single { createMovieDao(get()) }
    single<TaskRepository> { create<TaskRepositoryImpl>() }
    single<WeatherRepository> { create<WeatherRepositoryImpl>() }
}

fun createAppDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "database_todo").build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.taskDao()
