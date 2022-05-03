package com.example.task3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.Habit
import com.example.task3.data.App.Companion.questApi
import com.example.task3.data.repositories.DataBaseRepository
import com.example.task3.data.repositories.NetworkRepository
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import kotlin.coroutines.CoroutineContext


class Repository(): CoroutineScope {

    private val dataBaseRepository = DataBaseRepository()
    private val networkRepository = NetworkRepository(questApi)
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw  e }

    val habits: LiveData<List<Habit>> = dataBaseRepository.getHabits()

    suspend fun deleteHabit(habit: Habit){
        dataBaseRepository.deleteHabit(habit)
        networkRepository.deleteHabit(habit).awaitResponse()
    }

    suspend fun postHabit(){
        TODO()
    }

    suspend fun putHabit(habit: Habit){
        dataBaseRepository.putHabit(habit)
        networkRepository.putHabit(habit)

    }

    suspend fun updateHabit(habit: Habit){
        habit.date ++
        dataBaseRepository.updateHabit(habit)
        networkRepository.putHabit(habit)
        Log.e("ITEMS", networkRepository.getHabits().toString())
    }
}