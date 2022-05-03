package com.example.task3.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task3.Habit
import com.example.task3.converters.dataBaseConverters.HabitPriorityConverter
import com.example.task3.converters.dataBaseConverters.HabitTypeConverter


@Dao
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
@Suppress("AndroidUnresolvedRoomSqlReference")

interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM Habit WHERE uid = :id")
    fun getById(id: Long): LiveData<Habit?>

    @Insert
    fun insert(habit: Habit?)

    @Update
    fun update(habit: Habit?)

    @Delete
    fun delete(habit: Habit?)
}