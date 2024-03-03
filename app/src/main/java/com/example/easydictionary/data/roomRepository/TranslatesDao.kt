package com.example.easydictionary.data.roomRepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TranslatesDao {
    @Query("SELECT * FROM translates_table")
    fun list() : List<TranslateCache>
    @Query("SELECT * FROM translates_table WHERE id=:id")
    fun item(id : Long) : TranslateCache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(translate : TranslateCache)
    @Query("DELETE FROM translates_table WHERE id = :id")
    fun delete(id : Long)
}