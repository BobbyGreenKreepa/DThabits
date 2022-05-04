package com.example.task3.data.network

import androidx.lifecycle.LiveData
import com.example.task3.Habit
import com.example.task3.values.networkValues.NetworkAuthorization
import com.example.task3.values.networkValues.NetworkPaths
import com.example.task3.values.networkValues.NetworkToken
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET(NetworkPaths.DEFAULT_PATH)
    suspend fun getHabits(@Header(NetworkAuthorization.DEFAULT_AUTHORIZATION)
                                  token: String): List<Habit>

    @PUT(NetworkPaths.DEFAULT_PATH)
    suspend fun putHabit(@Header(NetworkAuthorization.DEFAULT_AUTHORIZATION)
                             token:String,
                         @Body habit: Habit): String

    @HTTP(method = "DELETE", path = NetworkPaths.DEFAULT_PATH, hasBody = true)
    suspend fun deleteHabit(@Header("Authorization") token: String, @Body uid: String): Void

    @POST(NetworkPaths.DONE_PATH)
    suspend fun postHabit(@Header(NetworkAuthorization.DEFAULT_AUTHORIZATION) token:String,
                  @Body date: Int, @Body habit_uid:  String): Void
}