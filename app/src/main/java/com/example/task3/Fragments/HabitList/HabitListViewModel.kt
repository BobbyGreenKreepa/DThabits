package com.example.task3.Fragments.HabitList

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.task3.Habit
import com.example.task3.DbRoom.HabitRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext


class HabitListViewModel : ViewModel(), Filterable, CoroutineScope {

    private val mutableHabit = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = mutableHabit
    private var habitsNotFilteredList = mutableHabit.value
    private  var repositoryDB: HabitRepository = HabitRepository()
    var habitType: Habit.HabitType? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{_, e -> throw e}

    init {
        onCreate()
    }

    private fun onCreate(){
        repositoryDB.habits.observeForever( Observer { it ->
            mutableHabit.value = it.filter { el -> el.type == habitType }
            habitsNotFilteredList = mutableHabit.value
        })
    }

    override fun onCleared() {
        TODO()
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

    fun deleteHabit(habit: Habit) {
        repositoryDB.removeItem(habit)
    }

    fun sortList(position: Int){
        when(position){
            0 -> mutableHabit.value = mutableHabit.value?.sortedBy {el-> el.id }
            1 -> mutableHabit.value = mutableHabit.value?.sortedBy {el-> el.period / el.time }
            2 -> mutableHabit.value = mutableHabit.value?.sortedBy {el-> el.priority.value }
        }
    }
}