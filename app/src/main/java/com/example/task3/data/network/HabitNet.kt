package com.example.task3.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
class HabitNet(
    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("type")
    var type: Int,

    @SerializedName("priority")
    var priority: Int,

    @SerializedName("count")
    var count: Int,

    @SerializedName("frequency")
    var frequency: Int,

    @SerializedName("color")
    var color: Int,

    @SerializedName("date")
    var date: Int,

    @PrimaryKey
    @SerializedName("uid")
    var uid: String = ""
)