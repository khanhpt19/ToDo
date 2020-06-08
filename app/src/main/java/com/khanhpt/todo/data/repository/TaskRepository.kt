package com.khanhpt.todo.data.repository

import com.khanhpt.todo.data.model.Task
import io.reactivex.Completable
import io.reactivex.Single

interface TaskRepository {
    fun getTasks(): Single<List<Task>>

    fun insertTask(task: Task): Completable

    fun deleteTask(task: Task): Completable
}
