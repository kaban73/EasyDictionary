package com.example.easydictionary.data.roomRepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translates_table")
data class TranslateCache(
    @PrimaryKey @ColumnInfo(name = "id") val id : Long,
    @ColumnInfo(name = "sourceText")val sourceText : String,
    @ColumnInfo(name = "translatedText")val translatedText : String
)