package com.example.task3.Fragments.HabitList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task3.*
import com.example.task3.Adapters.HabitAdapter
import com.example.task3.Adapters.SuperGestureDetector
import kotlinx.android.synthetic.main.habits_fragment.*


class HabitListFragment : Fragment(),
    LifecycleOwner,
    SuperGestureDetector.Callback,
    View.OnTouchListener{

    companion object {
        const val HABIT = "habit"
        const val TASK_KEY = "key"
        const val ADD_HABIT = "addHabit"
        const val CHANGE_HABIT = "changeHabit"
        const val HABIT_TYPE = "habit_type"
        fun newInstance(habitType: Habit.HabitType): HabitListFragment {
            val fragment = HabitListFragment()
            val bundle = Bundle()
            bundle.putSerializable(HABIT_TYPE, habitType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var superGestureDetector: SuperGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private lateinit var viewModel: HabitListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val habitType =
            this@HabitListFragment.arguments?.getSerializable(HABIT_TYPE) as Habit.HabitType

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitListViewModel() as T
            }
        }).get(HabitListViewModel::class.java)
        viewModel.habitType = habitType
        return inflater.inflate(R.layout.habits_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        add_habit_button.setOnClickListener { addHabit() }
        addAdapter()
        setupDetectors()
        observeViewModels()
        val bottomSheet = BottomSheet()
        childFragmentManager.beginTransaction()
            .replace(R.id.containerBottomSheet, bottomSheet)
            .commit()
    }

    private fun observeViewModels() {
        viewModel.habits.observe(viewLifecycleOwner, Observer {
            it.let {
                (habit_list.adapter as HabitAdapter).refreshHabits(
                    it
                )
            }
        })
    }

    private fun setupDetectors() {
        superGestureDetector = SuperGestureDetector(requireContext(),
            habit_list, SuperGestureDetector.LEFT)
        superGestureDetector.setCallback(this)
        gestureDetector = GestureDetector(context, superGestureDetector)
        habit_list.setOnTouchListener(null)
        habit_list.setOnTouchListener(this)
        habit_list.itemAnimator = null
    }


    private fun addAdapter() {
        habit_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HabitAdapter(context)
        }
    }

    private fun addHabit() {
        val navController = activity?.findNavController(R.id.my_nav_host_fragment)
        val bundle = Bundle()
        bundle.putString(TASK_KEY, ADD_HABIT)
        navController!!.navigate(R.id.action_goto_redactor, bundle)
    }

    private fun changeHabit(habit: Habit) {
        val navController = activity?.findNavController(R.id.my_nav_host_fragment)
        val bundle = Bundle()
        bundle.putString(TASK_KEY, CHANGE_HABIT)
        bundle.putSerializable(HABIT, habit)
        navController!!.navigate(R.id.action_goto_redactor, bundle)
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return if (p1?.action == MotionEvent.ACTION_UP || p1?.action == MotionEvent.ACTION_CANCEL) {
            superGestureDetector.onUp(p1)
        } else {
            gestureDetector.onTouchEvent(p1)
        }
    }

    override fun onItemTouched(position: Int) {
        changeHabit(viewModel.habits.value!![position])
    }

    override fun onItemRemove(position: Int) {
        if (position >= 0 && position < habit_list.adapter!!.itemCount) {
            viewModel.deleteHabit(viewModel.habits.value!![position])
            habit_list.adapter!!.notifyItemRemoved(position)
            superGestureDetector.confirmDeletion()
        }
    }
}