package com.example.task3.Fragments.HabitRedactor

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task3.DbRoom.HabitRepository
import com.example.task3.Habit
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RedactorHabitViewModel : ViewModel(), CoroutineScope {

    val color: MutableLiveData<Int> = MutableLiveData(Color.BLUE)
    private val repositoryDB = HabitRepository()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{ _, e -> throw e}

    fun addHabit(habit: Habit) = launch {
        withContext(Dispatchers.IO){
            repositoryDB.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit)= launch {
        withContext(Dispatchers.IO) {
            repositoryDB.updateHabit(habit)
        }
    }
}