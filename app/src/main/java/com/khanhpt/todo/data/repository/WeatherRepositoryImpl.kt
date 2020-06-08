package com.khanhpt.todo.data.repository

import com.khanhpt.todo.data.model.Weather
import com.khanhpt.todo.data.remote.api.WeatherApi
import com.khanhpt.todo.data.scheduler.SchedulerProvider
import io.reactivex.Single

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val schedulerProvider: SchedulerProvider
) : WeatherRepository {
    override fun getWeather(id: String): Single<Weather> =
        weatherApi.getWeather(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
}
