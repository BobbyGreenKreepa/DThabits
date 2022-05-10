package com.example.task3.converters.dataBaseConverters

import com.example.task3.values.habitValues.HabitType

class HabitTypeConverter {

    fun fromType(type: HabitType): Int {
        return type.value
    }

    fun toType(data: Int): HabitType {
        return when (data) {
             HabitType.BAD.value -> HabitType.BAD
            else -> HabitType.GOOD
        }
    }
}
