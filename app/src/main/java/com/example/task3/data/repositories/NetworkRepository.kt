package com.example.task3.data.repositories

import com.example.task3.Habit
import com.example.task3.data.network.ApiService
import com.example.task3.values.networkValues.NetworkToken

class NetworkRepository(private val questApi: ApiService) {


    suspend fun getHabits(): List<Habit> {
        return questApi.getHabits(
            NetworkToken.TOKEN
        )
    }
    suspend fun putHabit(habit: Habit): String = questApi.putHabit(
        NetworkToken.TOKEN,
        habit)

    fun deleteHabit(habit: Habit) = questApi.deleteHabit(
        NetworkToken.TOKEN,
        habit.uid)

    suspend fun postHabit(habit: Habit): Void = questApi.postHabit(
        NetworkToken.TOKEN,
        habit.date,
        habit.uid)

}