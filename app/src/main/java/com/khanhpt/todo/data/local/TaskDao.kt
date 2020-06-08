package com.khanhpt.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.khanhpt.todo.data.model.Task
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): Single<List<Task>>

    @Insert
    fun insert(task: Task): Completable

    @Delete
    fun delete(task: Task): Completable
}
