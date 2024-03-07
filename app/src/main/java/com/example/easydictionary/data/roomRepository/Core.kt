package com.example.easydictionary.data.roomRepository

import android.content.Context
import androidx.room.Room
import com.example.easydictionary.data.translateRepository.TranslateService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Core(
    private val context : Context
) {
    private val database by lazy {
        Room.databaseBuilder(
            context,
            TranslatesDataBase::class.java,
            "translates_database"
        ).build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    companion object {
        private const val URL = "https://ftapi.pythonanywhere.com"
    }
    fun service(): TranslateService = retrofit.create(TranslateService::class.java)
    fun dao() = database.translatesDao()
}