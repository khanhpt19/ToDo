package com.khanhpt.todo.data.repository

import com.khanhpt.todo.data.local.TaskDao
import com.khanhpt.todo.data.model.Task
import io.reactivex.Completable
import io.reactivex.Single

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override fun getTasks(): Single<List<Task>> =
        dao.getAll()

    override fun insertTask(task: Task): Completable =
        dao.insert(task)

    override fun deleteTask(task: Task): Completable =
        dao.delete(task)

}
