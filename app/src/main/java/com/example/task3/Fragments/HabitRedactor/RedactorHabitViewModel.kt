package com.example.task3.Fragments.HabitRedactor

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task3.DbRoom.HabitRepository
import com.example.task3.Habit
import com.example.task3.MainActivity
import com.example.task3.R

class RedactorHabitViewModel(): ViewModel() {

    val color: MutableLiveData<Int> = MutableLiveData(Color.BLUE)
    private val repositoryDB = HabitRepository()

    fun addHabit(habit: Habit){
        repositoryDB.addHabit(habit)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun updateHabit(habit: Habit){
        repositoryDB.updateHabit(habit)
    }
}