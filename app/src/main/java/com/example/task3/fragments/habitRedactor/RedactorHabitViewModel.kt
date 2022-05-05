package com.example.task3.fragments.habitRedactor

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.Habit
import com.example.task3.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RedactorHabitViewModel : ViewModel(){

    val color: MutableLiveData<Int> = MutableLiveData(Color.BLUE)
    private val repository = Repository()


    fun addHabit(habit: Habit) = viewModelScope.launch {
        withContext(Dispatchers.IO){repository.addHabit(habit)}
    }

    fun updateHabit(habit: Habit)= viewModelScope.launch {
        withContext(Dispatchers.IO){repository.updateHabit(habit)}
    }
}