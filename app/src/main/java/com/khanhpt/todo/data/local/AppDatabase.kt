package com.khanhpt.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khanhpt.todo.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
