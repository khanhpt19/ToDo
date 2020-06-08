package com.khanhpt.todo.di

import com.khanhpt.todo.data.scheduler.AppSchedulerProvider
import com.khanhpt.todo.data.scheduler.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.experimental.builder.create

val appModule = module {
    single { androidApplication().resources }
    single<SchedulerProvider> { create<AppSchedulerProvider>() }

}

val appModules = listOf(
    appModule,
    viewModelModule,
    repositoryModule,
    networkModule
)
