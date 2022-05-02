package com.example.task3.values.habitValues

import com.example.task3.MainActivity
import com.example.task3.R
import com.google.gson.annotations.SerializedName

enum class HabitType(val value: Int) {
    @SerializedName("0")
    GOOD(0),
    @SerializedName("1")
    BAD(1);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }

    override fun toString(): String {
        return if (this == BAD)
            MainActivity.CONTEXT.getString(R.string.bad_habit)
        else MainActivity.CONTEXT.getString(R.string.good_habit)
    }
}