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
import com.example.task3.R

class ColorPickerDialog: DialogFragment() {

    lateinit var buttons: ArrayList<View>;
    var onInputListener: OnInputListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.color_picker, container, false)
        buttons = view.findViewById<LinearLayout>(R.id.color_buttons).touchables
        buttons.forEach { it.setOnClickListener{returnColorCode(it as Button)}}
        return view
    }

    private fun returnColorCode(colorButton: Button){
        val background = colorButton.currentHintTextColor
        onInputListener!!.sendColor(background)
        dismiss()
    }

    interface OnInputListener {
        fun sendColor(color: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val fragment = parentFragment
            onInputListener = fragment as OnInputListener
        }

        catch (e: ClassCastException) {
            Log.e("Color Picker", "onAttach: ClassCastException: " + e.message)
        }
    }
}