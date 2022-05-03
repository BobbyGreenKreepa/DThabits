package com.example.task3.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task3.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDataBase: RoomDatabase() {
  abstract fun HabitDao(): HabitDao
}