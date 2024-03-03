package com.example.easydictionary.data.roomRepository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TranslateCache::class], version = 1)
abstract class TranslatesDataBase : RoomDatabase() {
    abstract fun translatesDao() : TranslatesDao
}