package com.example.task3.converters.dataBaseConverters

import com.example.task3.Habit
import com.example.task3.data.network.HabitNet

class DomainHabitToNetMapper:(Habit) -> HabitNet {

    private val habitTypeConverter = HabitTypeConverter()
    private val habitPriorityConverter = HabitPriorityConverter()


    override fun invoke(habit: Habit): HabitNet = HabitNet(
        title = habit.title,
        description = habit.description,
        type = habitTypeConverter.fromType(habit.type),
        priority = habitPriorityConverter.fromPriority(habit.priority),
        uid = habit.uid,
        date = habit.date,
        count = habit.count,
        color = habit.color,
        frequency = habit.frequency
    )
}