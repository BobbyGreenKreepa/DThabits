package com.example.task3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.Habit
import com.example.task3.data.App.Companion.questApi
import com.example.task3.data.network.HabitNet
import com.example.task3.data.repositories.DataBaseRepository
import com.example.task3.data.repositories.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


class Repository {

    companion object {
        const val HOST_EXCEPTION = "UnknownHostException"
    }

    private val dataBaseRepository = DataBaseRepository()
    private val networkRepository = NetworkRepository(questApi)

    init {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                loadRemoteData()
            }
        }
    }

    fun getHabits(): LiveData<List<Habit>> = dataBaseRepository.getHabits()

    suspend fun addHabit(habit: Habit) {

        try {
            habit.uid = networkRepository.putHabit(habit)?.uid ?: habit.title
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e("HTTP EXCEPTION", e.message())
        }

        dataBaseRepository.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {

        habit.date++
        dataBaseRepository.updateHabit(habit)

        try {
            habit.uid = networkRepository.putHabit(habit)?.uid ?: habit.title
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e("HTTP EXCEPTION", e.message())
        }
    }

    suspend fun deleteHabit(habit: Habit) {
        dataBaseRepository.deleteHabit(habit)

        try {
            networkRepository.deleteHabit(habit)
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e("HTTP EXCEPTION", e.message())
        }
    }

    private suspend fun loadRemoteData() {
        try {
            val remoteHabits = networkRepository.getHabits()
            insertFromServerToDB(remoteHabits)
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        }
    }

    private fun insertFromServerToDB(habits: List<Habit>?) {
        habits?.forEach {
            val habit = dataBaseRepository.getByUid(it.uid)
            if (habit == null)
                dataBaseRepository.addHabit(it)
        }
    }
}