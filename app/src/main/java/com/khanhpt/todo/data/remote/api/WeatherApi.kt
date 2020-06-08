package com.khanhpt.todo.data.remote.api

import com.khanhpt.todo.data.model.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("id") id: String
    ): Single<Weather>
}
