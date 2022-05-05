package com.example.task3.data.repositories

import com.example.task3.Habit
import com.example.task3.data.network.ApiService
import com.example.task3.data.network.HabitNet
import com.example.task3.values.networkValues.NetworkToken

class NetworkRepository(private val questApi: ApiService) {


    suspend fun getHabits(): List<HabitNet> {
        return questApi.getHabits(
            NetworkToken.TOKEN
        )
    }
    suspend fun putHabit(habitNet: HabitNet): String = questApi.putHabit(
        NetworkToken.TOKEN,
        habitNet)

    suspend fun deleteHabit(uid: String) = questApi.deleteHabit(
        NetworkToken.TOKEN,
        uid)

    suspend fun postHabit(habit: Habit): Void = questApi.postHabit(
        NetworkToken.TOKEN,
        habit.date,
        habit.uid!!)

}