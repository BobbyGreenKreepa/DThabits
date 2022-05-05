package com.example.task3.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task3.Habit
import com.example.task3.data.App.Companion.questApi
import com.example.task3.data.network.HabitNetConverter
import com.example.task3.data.repositories.DataBaseRepository
import com.example.task3.data.repositories.NetworkRepository
import kotlinx.coroutines.*
import okhttp3.internal.wait
import kotlin.coroutines.CoroutineContext


class Repository(): CoroutineScope {

    private val dataBaseRepository = DataBaseRepository()
    private val networkRepository = NetworkRepository(questApi)
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job + CoroutineExceptionHandler { _, e -> throw  e }

    val habits: LiveData<List<Habit>> = dataBaseRepository.getHabits()

    fun deleteHabit(habit: Habit) = launch{
            dataBaseRepository.deleteHabit(habit)
            networkRepository.deleteHabit(habit.uid!!)

    }

    suspend fun postHabit(){
        TODO()
    }

    fun addHabit(habit: Habit) = launch {
            dataBaseRepository.putHabit(habit)
            val uid = networkRepository.putHabit(HabitNetConverter().toHabitNet(habit))
            habit.uid = uid
    }

    fun updateHabit(habit: Habit) = launch{
            habit.date ++
            dataBaseRepository.updateHabit(habit)
            networkRepository.putHabit(HabitNetConverter().toHabitNet(habit))
    }
}