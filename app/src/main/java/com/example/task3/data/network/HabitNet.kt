package com.example.task3.data.network

import com.example.task3.Habit
import com.example.task3.converters.dataBaseConverters.HabitPriorityConverter
import com.example.task3.converters.dataBaseConverters.HabitTypeConverter
import com.google.gson.annotations.SerializedName

data class HabitNet(
    val title: String,
    val name: String,
    val description: String,
    val type: Int,
    val priority: Int,
    @SerializedName("count")
    val time: Int,
    @SerializedName("frequency")
    val period: Int,
    var color: Int,
    var uid: String?,
    var date: Int
)

class HabitNetConverter{

    fun toHabitNet(habit: Habit): HabitNet {
        return HabitNet(
            habit.title,
            habit.name,
            habit.description,
            HabitTypeConverter().fromType(habit.type),
            HabitPriorityConverter().fromPriority(habit.priority),
            habit.time,
            habit.period,
            habit.color,
            habit.uid,
            habit.date
        )
    }

    fun toHabit(habitNet: HabitNet): Habit {
        val habit =  Habit(
            habitNet.name,
            habitNet.description,
            HabitTypeConverter().toType(habitNet.type),
            HabitPriorityConverter().toPriority(habitNet.priority),
            habitNet.time,
            habitNet.period,
            habitNet.color
        )
        habit.uid = habitNet.uid
        return habit
    }


}