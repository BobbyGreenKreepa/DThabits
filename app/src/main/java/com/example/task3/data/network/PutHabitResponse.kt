package com.example.task3.data.network

import com.google.gson.annotations.SerializedName

data class PutHabitResponse(

    @SerializedName("uid")
    val uid: String
)