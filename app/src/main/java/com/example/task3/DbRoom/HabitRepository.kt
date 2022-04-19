package com.example.task3.DbRoom

import androidx.lifecycle.LiveData
import com.example.task3.Habit

class HabitRepository{

    val habits: LiveData<List<Habit>> = App.db.HabitDao().getAll()

    fun addHabit(habit: Habit) {
        App.db.HabitDao().insert(habit)
    }

    fun updateHabit(newHabit: Habit) {
        App.db.HabitDao().update(newHabit)
    }

    fun removeItem(habit: Habit) {
        App.db.HabitDao().delete(habit)
    }
}