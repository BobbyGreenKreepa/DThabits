package com.example.task3.Fragments.HabitList

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.task3.Habit
import com.example.task3.DbRoom.HabitRepository


class HabitListViewModel : ViewModel(), Filterable {

    private val mutableHabit = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = mutableHabit
    private  var repositoryDB: HabitRepository = HabitRepository()
    var habitType: Habit.HabitType? = null
    private var habitsNotFilteredList = habits



    init {
        onCreate()
    }


    private fun onCreate(){
        repositoryDB.habits.observeForever( Observer { it ->
            mutableHabit.value = it.filter { el -> el.type == habitType } })
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                val searchResult = FilterResults()
                if (searchString.isEmpty())
                    searchResult.values =  habitsNotFilteredList
                else
                    searchResult.values = habits.value?.filter{ it.name.contains(searchString)}
                return searchResult
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mutableHabit.value = results?.values as List<Habit>?
            }
        }
    }

    fun getItems() = habits.value

    fun habitsMoved(startPosition: Int, nextPosition: Int) {
        val habits = mutableHabit.value as MutableList
        val habit = habits[startPosition]
        habits[startPosition] = habits[nextPosition]
        habits[nextPosition] = habit
    }

    fun deleteHabit(habit: Habit) {
        repositoryDB.removeItem(habit)
    }

    fun sortList(position: Int){
        when(position){
            0 -> mutableHabit.value = mutableHabit.value!!.sortedBy {el-> el.id }
            1 -> mutableHabit.value = mutableHabit.value!!.sortedBy {el-> el.period / el.time }
            2 -> mutableHabit.value = mutableHabit.value!!.sortedBy {el-> el.priority.value }
        }
    }
}