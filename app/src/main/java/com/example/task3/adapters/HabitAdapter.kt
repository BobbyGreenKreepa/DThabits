package com.example.task3.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.Habit
import com.example.task3.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class HabitAdapter(private val context: Context?)
    : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private var habits: List<Habit> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = habits.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshHabits(habitList: List<Habit>) {
        this.habits = habitList
        notifyDataSetChanged()
    }

    inner class HabitViewHolder(override val containerView: View) :
        SuperGestureDetector.SwipableViewHolder(containerView), LayoutContainer {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(habit: Habit) {
            val times = context?.resources?.getQuantityString(R.plurals.count, habit.count, habit.count)
            val days = context?.resources?.getQuantityString(R.plurals.times, habit.frequency, habit.frequency)

            containerView.habit_name.text = habit.title
            containerView.habit_description.text = "${context?.getString(R.string.repeat)} $times ${context?.getString(R.string.`in`)} $days"
            containerView.cardView.backgroundTintList = ColorStateList.valueOf(habit.color)
        }

        override val swipableView: View?
            get() = containerView.swipable
    }
}