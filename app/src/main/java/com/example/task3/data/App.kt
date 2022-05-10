package com.example.task3.data

import android.app.Application
import androidx.room.Room
import com.example.task3.data.dataBase.HabitsDataBase
import com.example.task3.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    companion object {
        lateinit var instance: App
        lateinit var db: HabitsDataBase
        lateinit var questApi: ApiService
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(
            applicationContext,
            HabitsDataBase::class.java, "database4.0")
            .allowMainThreadQueries()
            .build()
        configureRetrofit()
    }

    private fun configureRetrofit(){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{chain ->
                val request: Request = chain.request()
                var response = chain.proceed(request)
                var connectionTryCounter = 0
                while (!(response.isSuccessful) && connectionTryCounter < 5) {
                    connectionTryCounter++
                    response = chain.proceed(request)
                }
                response
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://droid-test-server.doubletapp.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        questApi = retrofit.create(ApiService::class.java)
    }
}