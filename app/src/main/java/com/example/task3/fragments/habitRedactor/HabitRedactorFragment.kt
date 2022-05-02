package com.example.task3.fragments.habitRedactor

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.task3.*
import com.example.task3.values.habitValues.HabitPriority
import com.example.task3.values.habitValues.HabitType
import com.example.task3.fragments.habitList.HabitListFragment
import kotlinx.android.synthetic.main.redactor_fragment.*
import kotlin.math.absoluteValue

class  HabitRedactorFragment: Fragment(){

    private lateinit var viewModel: RedactorHabitViewModel
    lateinit var colorDialog: DialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RedactorHabitViewModel() as T
            }
        }).get(RedactorHabitViewModel::class.java)
        return inflater.inflate(R.layout.redactor_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.color.observe(viewLifecycleOwner, Observer {
            color_button.backgroundTintList = ColorStateList.valueOf(it)
        })
        colorDialog = ColorPickerDialog()
        when (arguments?.getString(HabitListFragment.TASK_KEY)) {

            HabitListFragment.ADD_HABIT -> habit_save_button.setOnClickListener { saveNewData() }
            HabitListFragment.CHANGE_HABIT -> {
                val habit = requireArguments().getSerializable(HabitListFragment.HABIT)
                habit_save_button.setOnClickListener {
                    saveChangedData(habit as Habit)
                }
                updateText(habit as Habit)
            }
            else -> throw IllegalArgumentException("Name required")
        }
        color_button.setOnClickListener {
            colorDialog.show(childFragmentManager, "Color Picker")
        }
    }

    private fun updateText(habit: Habit) {
        edit_habit_name.setText(habit.name)
        edit_description.setText(habit.description)
        spinner.setSelection(habit.priority.value)
        edit_days.setText(habit.period.toString())
        edit_times.setText(habit.time.toString())
        when (habit.type) {
            HabitType.GOOD -> radioGroup.check(R.id.first_radio)
            HabitType.BAD -> radioGroup.check(R.id.second_radio)
        }
        viewModel.color.value = habit.color
        val state = ColorStateList.valueOf(habit.color.absoluteValue)
        color_button.backgroundTintList = state
    }

    private fun fillHabitData(): Boolean {
        var result = true

        if (radioGroup.checkedRadioButtonId == -1) {
            result = false
            habit_current_type.setTextColor(Color.RED)
        }

        if (edit_days.text.isEmpty() || edit_times.text.isEmpty()) {
            result = false
            habit_frequency.setTextColor(Color.RED)
        }
        if (edit_habit_name.text.isEmpty()) {
            result = false
            edit_habit_name.setHintTextColor(Color.RED)
        }
        return result
    }

    private fun backToHabitList() {
        val navController = activity?.findNavController(R.id.my_nav_host_fragment)
        navController!!.navigate(R.id.action_redactor_to_viewPagerFragment)
    }

    private fun saveNewData() {
        if (fillHabitData()) {
            val habit = collectHabit()
            viewModel.addHabit(habit)
            backToHabitList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun saveChangedData(habit: Habit) {
        if (fillHabitData()) {
            val newHabit = collectHabit()
            newHabit.uid = habit.uid
            viewModel.updateHabit(newHabit)
            backToHabitList()
        }
    }

    private fun collectHabit(): Habit {
        return Habit(
            edit_habit_name.text.toString(), edit_description.text.toString(),
            HabitType.fromInt(radioGroup.indexOfChild(requireView().findViewById(radioGroup.checkedRadioButtonId))),
            HabitPriority.fromInt(spinner.selectedItemPosition),
            Integer.valueOf(edit_times.text.toString()),
            Integer.valueOf(edit_days.text.toString()),
            viewModel.color.value!!
        )
    }
}