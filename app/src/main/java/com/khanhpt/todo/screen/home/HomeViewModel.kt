package com.khanhpt.todo.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khanhpt.todo.data.model.Task
import com.khanhpt.todo.data.model.Weather
import com.khanhpt.todo.data.repository.TaskRepository
import com.khanhpt.todo.data.repository.WeatherRepository
import com.khanhpt.todo.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val taskRepository: TaskRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val tasks = MutableLiveData<List<Task>>()
    val addNewTaskSuccess = SingleLiveEvent<Unit>()
    val removeTaskSuccess = SingleLiveEvent<Unit>()
    val errorMessage = SingleLiveEvent<Unit>()
    val errorWeather = MutableLiveData<String>()
    val weatherData = MutableLiveData<Weather>()

    fun getTasks() {
        compositeDisposable.add(
            taskRepository.getTasks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tasks.value = it
                }, {
                    errorMessage.call()
                })
        )
    }

    fun insertTask(task: Task) {
        compositeDisposable.add(
            taskRepository.insertTask(task).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addNewTaskSuccess.call()
                }, {
                    errorMessage.call()
                })
        )
    }

    fun removeTask(task: Task) {
        compositeDisposable.add(
            taskRepository.deleteTask(task).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    removeTaskSuccess.call()
                }, {
                    errorMessage.call()
                })
        )
    }

    // get weather info of Hanoi City with id:1581130
    fun getWeather() {
        compositeDisposable.add(
            weatherRepository.getWeather("1581130").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherData.value = it
                }, {
                    errorWeather.value = it.message
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
