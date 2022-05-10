package com.example.task3


import androidx.room.*
import com.example.task3.converters.dataBaseConverters.HabitPriorityConverter
import com.example.task3.converters.dataBaseConverters.HabitTypeConverter
import com.example.task3.values.habitValues.HabitPriority
import com.example.task3.values.habitValues.HabitType
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

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