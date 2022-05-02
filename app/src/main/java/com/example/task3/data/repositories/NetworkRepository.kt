package com.example.task3.data.repositories

import com.example.task3.Habit
import com.example.task3.data.network.ApiService
import com.example.task3.values.networkValues.NetworkToken

class NetworkRepository(private val apiService: ApiService) {


    suspend fun getHabits(): ArrayList<Habit> = apiService.getHabits(
        NetworkToken.TOKEN)

    suspend fun putHabit(habit: Habit): String = apiService.putHabit(
        NetworkToken.TOKEN,
        habit)

    suspend fun deleteHabit(habit: Habit): Void = apiService.deleteHabit(
        NetworkToken.TOKEN,
        habit.uid)

    suspend fun postHabit(habit: Habit): Void = apiService.postHabit(
        NetworkToken.TOKEN,
        habit.date,
        habit.uid)

}