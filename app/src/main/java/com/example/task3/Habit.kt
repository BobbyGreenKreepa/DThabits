package com.example.task3


import com.example.task3.values.habitValues.HabitPriority
import com.example.task3.values.habitValues.HabitType
import java.io.Serializable

data class Habit(
    val title: String,
    val description: String,
    val type: HabitType,
    val priority: HabitPriority,
    val count: Int,
    val frequency: Int,
    var color: Int,
    var uid: String = "",
    var date: Int = 1
): Serializable