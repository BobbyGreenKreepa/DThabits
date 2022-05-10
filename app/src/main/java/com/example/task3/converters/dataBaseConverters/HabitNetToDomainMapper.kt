package com.example.task3.converters.dataBaseConverters

import com.example.task3.Habit
import com.example.task3.data.network.HabitNet

class HabitNetToDomainMapper: (HabitNet) -> Habit {

    private val habitTypeConverter = HabitTypeConverter()
    private val habitPriorityConverter = HabitPriorityConverter()

    override fun invoke(net: HabitNet): Habit = Habit(
        title = net.title,
        description = net.description,
        type = habitTypeConverter.toType(net.type),
        priority = habitPriorityConverter.toPriority(net.priority),
        color = net.color,
        count = net.count,
        frequency = net.frequency,
        uid = net.uid,
        date = net.date
    )
}