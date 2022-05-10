package com.example.task3.fragments.habitList

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.*
import com.example.task3.Habit
import com.example.task3.data.Repository
import com.example.task3.values.habitValues.HabitType
import kotlinx.coroutines.*


class HabitListViewModel : ViewModel(), Filterable {

    private val mutableHabit = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = mutableHabit
    private lateinit var observer: Observer<List<Habit>>

    private var habitsNotFilteredList = mutableHabit.value
    private  var repository: Repository = Repository()
    var habitType: HabitType? = null

    init {
        onCreate()
    }

    private fun onCreate(){
            observer = Observer<List<Habit>> { it ->
                mutableHabit.value = it.filter { el -> el.type == habitType }
            habitsNotFilteredList = mutableHabit.value}
        viewModelScope.launch { repository.getHabits().observeForever(observer) }
        }

    override fun onCleared() {
        habits.removeObserver(observer)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                val searchResult = FilterResults()
                if (searchString.isEmpty())
                    searchResult.values =  habitsNotFilteredList
                else
                    searchResult.values = habits.value?.filter{ it.title.contains(searchString)}
                return searchResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mutableHabit.value = results?.values as List<Habit>?
            }
        }
    }

    fun deleteHabit(habit: Habit) = viewModelScope.launch {
        withContext(Dispatchers.IO){repository.deleteHabit(habit)}
    }

    fun sortList(position: Int) = viewModelScope.launch {
        when (position) {
            0 -> mutableHabit.postValue(mutableHabit.value?.sortedBy {el -> el.uid })
            1 -> mutableHabit.postValue(mutableHabit.value?.sortedBy {el -> el.frequency / el.count })
            2 -> mutableHabit.postValue(mutableHabit.value?.sortedBy {el -> el.priority?.value })
        }
    }
}