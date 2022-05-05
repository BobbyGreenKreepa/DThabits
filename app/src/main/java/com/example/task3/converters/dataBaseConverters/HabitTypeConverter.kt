package com.example.task3.converters.dataBaseConverters

import com.example.task3.values.habitValues.HabitType

class HabitTypeConverter {

    @androidx.room.TypeConverter
    fun fromType(type: HabitType): Int {
        return type.value
    }

    @androidx.room.TypeConverter
    fun toType(data: Int): HabitType {
        return when (data) {
             HabitType.BAD.value -> HabitType.BAD
            else -> HabitType.GOOD
        }
    }
}
