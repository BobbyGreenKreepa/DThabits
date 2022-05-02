package com.example.task3.data.repositories

import androidx.lifecycle.LiveData
import com.example.task3.Habit
import com.example.task3.data.App

class DataBaseRepository {

    fun getHabits(){
        App.db.HabitDao().getAll()
    }

    fun addHabit(habit: Habit) {
        App.db.HabitDao().insert(habit)
    }

    fun updateHabit(newHabit: Habit) {
        App.db.HabitDao().update(newHabit)
    }

    fun deleteHabit(habit: Habit) {
        App.db.HabitDao().delete(habit)
    }
}