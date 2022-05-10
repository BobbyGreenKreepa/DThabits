package com.example.task3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.Habit
import com.example.task3.data.App.Companion.questApi
import com.example.task3.data.repositories.DataBaseRepository
import com.example.task3.data.repositories.NetworkRepository


class Repository{

    private val dataBaseRepository = DataBaseRepository()
    private val networkRepository = NetworkRepository(questApi)


    suspend fun getHabits() : LiveData<List<Habit>> {
        val localHabits = dataBaseRepository.getHabits()
        val remoteHabits = MutableLiveData(networkRepository.getHabits())
        return if (remoteHabits.value.isNullOrEmpty()) localHabits else remoteHabits
    }

    suspend fun addHabit(habit: Habit) {
        habit.uid = networkRepository.putHabit(habit)
        dataBaseRepository.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        networkRepository.putHabit(habit)
        dataBaseRepository.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        networkRepository.deleteHabit(habit)
        dataBaseRepository.deleteHabit(habit)
    }

}