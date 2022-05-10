package com.example.task3.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
class HabitNet(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("type")
    val type: Int,

    @SerializedName("priority")
    val priority: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("frequency")
    val frequency: Int,

    @SerializedName("color")
    val color: Int,

    @SerializedName("date")
    val date: Int,

    @PrimaryKey
    @SerializedName("uid")
    var uid: String = ""
)