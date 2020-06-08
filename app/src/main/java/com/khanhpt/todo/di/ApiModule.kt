package com.khanhpt.todo.di

import android.content.Context
import com.khanhpt.todo.BuildConfig
import com.khanhpt.todo.data.remote.api.WeatherApi
import com.khanhpt.todo.di.Properties.TIME_OUT
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(named("logging")) { createLoggingInterceptor() }
    single(named("header")) { createHeaderInterceptor() }
    single { createOkHttpClient(get(), get(named("header")), get(named("logging"))) }
    single { createAppRetrofit(get()) }
    single { createAPIService(get()) }
}

object Properties {
    const val TIME_OUT = 10L
}

fun createOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong()
    return Cache(context.cacheDir, size)
}

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return logging
}

fun createHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder().addQueryParameter("appid", BuildConfig.KEY_APP_ID)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }
}

fun createOkHttpClient(
    cache: Cache,
    header: Interceptor,
    logging: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .build()
}

fun createAppRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
}

fun createAPIService(retrofit: Retrofit): WeatherApi {
    return retrofit.create(WeatherApi::class.java)
}
