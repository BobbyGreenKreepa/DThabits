package com.example.task3.values.habitValues

import com.example.task3.MainActivity
import com.example.task3.R
import com.google.gson.annotations.SerializedName

enum class HabitPriority(val value: Int) {
    @SerializedName("2")
    HIGH(2),
    @SerializedName("1")
    MEDIUM(1),
    @SerializedName("0")
    LOW(0);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }

    override fun toString(): String {
        return when (this) {
            HIGH -> MainActivity.CONTEXT.getString(R.string.high)
            MEDIUM -> MainActivity.CONTEXT.getString(R.string.medium)
            LOW -> MainActivity.CONTEXT.getString(R.string.low)
        }
    }
}