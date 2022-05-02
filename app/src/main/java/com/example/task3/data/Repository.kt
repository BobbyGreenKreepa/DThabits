package com.example.task3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.Habit
import com.example.task3.data.repositories.DataBaseRepository
import com.example.task3.data.repositories.NetworkRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Repository(private val dataBaseRepository: DataBaseRepository,
                 private val networkRepository: NetworkRepository): CoroutineScope {


    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw  e }

    private val mutableHabits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = mutableHabits

    init {
        launch { mutableHabits.value = dataBaseRepository.getHabits() }
    }

    suspend fun deleteHabit(habit: Habit){
        networkRepository.deleteHabit(habit)
        dataBaseRepository.deleteHabit(habit)

    }

    suspend fun postHabit(){
        TODO()
    }

    suspend fun putHabit(habit: Habit){
        networkRepository.putHabit(habit)
    }

    suspend fun updateHabit(habit: Habit){
        networkRepository.putHabit(habit)
        dataBaseRepository.updateHabit(habit)
    }
}