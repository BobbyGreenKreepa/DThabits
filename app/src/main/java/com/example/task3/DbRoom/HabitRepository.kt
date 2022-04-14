package com.example.task3.DbRoom

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.task3.Habit

class HabitRepository: Application() {

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