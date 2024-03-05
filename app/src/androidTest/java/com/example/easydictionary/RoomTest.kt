package com.example.easydictionary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.easydictionary.data.roomRepository.TranslateCache
import com.example.easydictionary.data.roomRepository.TranslatesDao
import com.example.easydictionary.data.roomRepository.TranslatesDataBase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var db : TranslatesDataBase
    private lateinit var dao : TranslatesDao

    @Before
    fun setUp() {
        val context : Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, TranslatesDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.translatesDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_add() {
        assertEquals(emptyList<TranslateCache>(), dao.list())

        val cache = TranslateCache(
            id = 1L,
            sourceText = "first",
            translatedText = "первый"
        )
        dao.add(translate = cache)
        assertEquals(listOf(
            TranslateCache(
                id = 1L,
                sourceText = "first",
                translatedText = "первый"
            )), dao.list())
    }

    @Test
    fun test_item() {
        assertEquals(emptyList<TranslateCache>(), dao.list())

        val cache = TranslateCache(
            id = 1L,
            sourceText = "first",
            translatedText = "первый"
        )
        dao.add(translate = cache)
        assertEquals(TranslateCache(
            id = 1L,
            sourceText = "first",
            translatedText = "первый"
        ), dao.item(1L))

        val next = TranslateCache(
            id = 2L,
            sourceText = "second",
            translatedText = "второй"
        )
        dao.add(translate = next)
        assertEquals(TranslateCache(
            id = 2L,
            sourceText = "second",
            translatedText = "второй"
        ), dao.item(2L))

        assertEquals(listOf(
            TranslateCache(
                id = 1L,
                sourceText = "first",
                translatedText = "первый"
            ),
            TranslateCache(
                id = 2L,
                sourceText = "second",
                translatedText = "второй"
            )), dao.list())
    }

    @Test
    fun test_delete() {
        val cache = TranslateCache(
            id = 1L,
            sourceText = "first",
            translatedText = "первый"
        )
        dao.add(translate = cache)


        val next = TranslateCache(
            id = 2L,
            sourceText = "second",
            translatedText = "второй"
        )
        dao.add(translate = next)

        assertEquals(listOf(
            TranslateCache(
                id = 1L,
                sourceText = "first",
                translatedText = "первый"
            ),
            TranslateCache(
                id = 2L,
                sourceText = "second",
                translatedText = "второй"
            )), dao.list())

        dao.delete(2L)
        assertEquals(listOf(
            TranslateCache(
                id = 1L,
                sourceText = "first",
                translatedText = "первый"
            )), dao.list())
    }
}