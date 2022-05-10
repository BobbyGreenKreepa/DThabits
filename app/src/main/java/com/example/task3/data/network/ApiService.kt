package com.example.task3.data.network

import com.example.task3.data.repositories.NetworkRepository
import retrofit2.http.*


interface ApiService {

    @GET(NetworkRepository.PATH)
    suspend fun getHabits(
        @Header(NetworkRepository.HEADER)
        token: String
    ): List<HabitNet>

    @PUT(NetworkRepository.PATH)
    suspend fun putHabit(
        @Header(NetworkRepository.HEADER)
        token:String,
        @Body habit: HabitNet
    ): String

    @HTTP(method = "DELETE", path = NetworkRepository.PATH, hasBody = true)
    suspend fun deleteHabit(
        @Header(NetworkRepository.HEADER)
        token: String,
        @Body uid: String
    )

    @POST(NetworkRepository.HABIT_DONE_PATH)
    suspend fun postHabit(
        @Header(NetworkRepository.HEADER) token:String,
        @Body date: Int,
        @Body habit_uid:  String)
}