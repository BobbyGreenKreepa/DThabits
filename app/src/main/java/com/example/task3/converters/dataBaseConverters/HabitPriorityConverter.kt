package com.example.task3.converters.dataBaseConverters

import com.example.task3.values.habitValues.HabitPriority

class HabitPriorityConverter {

    fun fromPriority(priority: HabitPriority): Int {
        return priority.value
    }

    fun toPriority(data: Int): HabitPriority {
        return when (data) {
            HabitPriority.HIGH.value -> HabitPriority.HIGH
            HabitPriority.MEDIUM.value -> HabitPriority.MEDIUM
            else -> HabitPriority.LOW
        }
    }
}