package com.example.task3


import androidx.room.*
import com.example.task3.converters.dataBaseConverters.HabitPriorityConverter
import com.example.task3.converters.dataBaseConverters.HabitTypeConverter
import com.example.task3.values.habitValues.HabitPriority
import com.example.task3.values.habitValues.HabitType
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
data class Habit(
    val name: String,
    val description: String,
    val type: HabitType,
    val priority: HabitPriority,
    @SerializedName("count")
    val time: Int,
    @SerializedName("frequency")
    val period: Int,
    var color: Int
) : Serializable {

    @PrimaryKey
    var uid = UUID.randomUUID().toString()
    var date: Int = 0
}