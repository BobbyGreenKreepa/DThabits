package com.example.task3


import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(Habit.TypeConverter::class, Habit.PriorityConverter::class)
data class Habit(
    val name: String,
    val description: String,
    val type: HabitType,
    val priority: HabitPriority,
    val time: Int,
    val period: Int,
    var color: Int
) : Serializable {

    @PrimaryKey
    var id = UUID.randomUUID().toString()

    enum class HabitType(val value: Int) {
        GOOD(0),
        BAD(1);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }

        override fun toString(): String {
            return if (this == BAD)
                MainActivity.CONTEXT.getString(R.string.bad_habit)
            else MainActivity.CONTEXT.getString(R.string.good_habit)
        }
    }

    enum class HabitPriority(val value: Int) {
        HIGH(2),
        MEDIUM(1),
        LOW(0);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }

        override fun toString(): String {
            return when (this) {
                HIGH -> MainActivity.CONTEXT.getString(R.string.high)
                MEDIUM -> MainActivity.CONTEXT.getString(R.string.medium)
                LOW -> MainActivity.CONTEXT.getString(R.string.low)
            }
        }
    }

    class TypeConverter {
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

    class PriorityConverter {
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

}