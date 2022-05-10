package com.example.task3.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.Habit
import com.example.task3.converters.dataBaseConverters.DomainHabitToNetMapper
import com.example.task3.converters.dataBaseConverters.HabitNetToDomainMapper
import com.example.task3.data.App

class DataBaseRepository {

    private val domainHabitToNetMapper = DomainHabitToNetMapper()
    private val habitNetToDomainMapper = HabitNetToDomainMapper()

    fun getHabits(): LiveData<List<Habit>> = MutableLiveData(App.db.HabitDao().getAll().value?.map(habitNetToDomainMapper))

    fun addHabit(habit: Habit) {
        App.db.HabitDao().insert(domainHabitToNetMapper.invoke(habit))
    }

    fun updateHabit(habit: Habit) {
        App.db.HabitDao().update(domainHabitToNetMapper.invoke(habit))
    }

    fun deleteHabit(habit: Habit) {
        App.db.HabitDao().delete(domainHabitToNetMapper.invoke(habit))
    }
}