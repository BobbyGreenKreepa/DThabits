package com.example.task3.data.repositories

import com.example.task3.Habit
import com.example.task3.converters.dataBaseConverters.DomainHabitToNetMapper
import com.example.task3.converters.dataBaseConverters.HabitNetToDomainMapper
import com.example.task3.data.network.ApiService

class NetworkRepository(private val questApi: ApiService) {

    companion object {
        const val TOKEN = "7af52d9e-b8d1-4e9c-9682-be3e8dbddff2"
        const val PATH = "api/habit"
        const val HABIT_DONE_PATH = "api/done_habit"
        const val HEADER = "Authorization"

    }

    private val domainHabitToNetMapper = DomainHabitToNetMapper()
    private val habitNetToDomainMapper = HabitNetToDomainMapper()

    suspend fun getHabits(): List<Habit> = questApi.getHabits(TOKEN).map(habitNetToDomainMapper)

    suspend fun putHabit(habit: Habit): String = questApi.putHabit(
        TOKEN,
        domainHabitToNetMapper(habit)
    )

    suspend fun deleteHabit(habit: Habit) = questApi.deleteHabit(
        TOKEN,
        habit.uid
    )

    suspend fun postHabit(habit: Habit) = questApi.postHabit(
        TOKEN,
        habit.date,
        habit.uid
    )
}