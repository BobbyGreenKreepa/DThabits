package com.example.task3.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task3.Habit
import com.example.task3.data.network.HabitNet

@Database(entities = [HabitNet::class], version = 4)
abstract class HabitsDataBase: RoomDatabase() {
  abstract fun HabitDao(): HabitDao
}