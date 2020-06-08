package com.khanhpt.todo.data.repository

import com.khanhpt.todo.data.model.Weather
import io.reactivex.Single

interface WeatherRepository {
    fun getWeather(id: String): Single<Weather>
}
