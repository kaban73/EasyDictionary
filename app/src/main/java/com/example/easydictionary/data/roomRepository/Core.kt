package com.example.easydictionary.data.roomRepository

import android.content.Context
import androidx.room.Room

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
    fun dao() = database.translatesDao()
}