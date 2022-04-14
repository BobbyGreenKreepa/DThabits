package com.example.task3.Fragments.HabitRedactor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task3.R

class ColorPickerDialog: DialogFragment() {

    lateinit var buttons: ArrayList<View>;

    private lateinit var viewModel: RedactorHabitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(requireParentFragment()).get(RedactorHabitViewModel::class.java)
        val view =  inflater.inflate(R.layout.color_picker, container, false)
        buttons = view.findViewById<LinearLayout>(R.id.color_buttons).touchables
        buttons.forEach { it.setOnClickListener{returnColorCode(it as Button)}}
        return view
    }

    private fun returnColorCode(colorButton: Button){
        val background = colorButton.currentHintTextColor
        viewModel.color.value = background
        dismiss()
    }
}