package com.example.task3.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task3.data.network.HabitNet


@Dao
interface HabitDao {
    @Query("SELECT * FROM HabitNet")
    fun getAll(): LiveData<List<HabitNet>>

    @Insert
    fun insert(habitNet: HabitNet?)

    @Update
    fun update(habitNet: HabitNet?)

    @Delete
    fun delete(habitNet: HabitNet?)
}