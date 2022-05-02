package com.example.task3.converters.dataBaseConverters

import com.example.task3.values.habitValues.HabitType

class HabitTypeConverter {

    @androidx.room.TypeConverter
    fun fromType(type: HabitType): String {
        return type.toString()
    }

    @androidx.room.TypeConverter
    fun toType(data: String): HabitType {
        return when (data) {
            "Вредная" -> HabitType.BAD
            else -> HabitType.GOOD
        }
    }
}
