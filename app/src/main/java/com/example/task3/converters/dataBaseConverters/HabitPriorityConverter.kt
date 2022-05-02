package com.example.task3.converters.dataBaseConverters

import com.example.task3.values.habitValues.HabitPriority

class HabitPriorityConverter {

    @androidx.room.TypeConverter
    fun fromPriority(priority: HabitPriority): String {
        return priority.toString()
    }

    @androidx.room.TypeConverter
    fun toPriority(data: String): HabitPriority {
        return when (data) {
            "Высокий" -> HabitPriority.HIGH
            "Средний" -> HabitPriority.MEDIUM
            else -> HabitPriority.LOW
        }
    }
}