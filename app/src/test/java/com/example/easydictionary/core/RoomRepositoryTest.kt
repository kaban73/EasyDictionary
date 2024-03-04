package com.example.easydictionary.core

import com.example.easydictionary.data.roomRepository.TranslateCache
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.data.roomRepository.TranslatesDao
import com.example.easydictionary.data.roomRepository.Now
import com.example.easydictionary.data.translateRepository.TranslateResponse
import com.example.easydictionary.list.Translate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RoomRepositoryTest {
    private lateinit var now : FakeNow
    private lateinit var translatesDao : FakeTranslatesDao
    private lateinit var repository : RoomRepository.All

    @Before
    fun setup() {
        now = FakeNow.Base(666L)
        translatesDao = FakeTranslatesDao.Base()
        repository = RoomRepository.Base(
            translatesDao = translatesDao,
            now = now
        )
    }

    @Test
    fun test_add() {
        translatesDao.expectList(
            listOf(
                TranslateCache(id = 0L, sourceText = "привет", translatedText = "hello"),
                TranslateCache(id = 1L, sourceText = "пока", translatedText = "goodbye")
            )
        )

        val actual : List<Translate> = repository.list()
        val expected = listOf(
            Translate(0, "привет", "hello"),
            Translate(1, "пока", "goodbye")
        )
        assertEquals(expected, actual)

        repository.add(value = TranslateResponse(sourceText = "cool", translatedText = "крутой"))
        translatesDao.checkList(
            listOf(
                TranslateCache(id = 0L, sourceText = "привет", translatedText = "hello"),
                TranslateCache(id = 1L, sourceText = "пока", translatedText = "goodbye"),
                TranslateCache(id = 667L, sourceText = "cool", translatedText = "крутой"),
            )
        )
    }

    @Test
    fun test_item() {
        repository.add(value = TranslateResponse(sourceText = "first", translatedText = "первый"))
        translatesDao.checkList(
            listOf(
                TranslateCache(id = 667L, sourceText = "first", translatedText = "первый")
            )
        )

        val actual : Translate = repository.item(id = 667L)
        val expected = Translate(id = 667L, sourceText = "first", translatedText = "первый")
        assertEquals(expected, actual)
    }

    @Test
    fun test_delete() {
        repository.add(value = TranslateResponse(sourceText = "first", translatedText = "первый"))
        translatesDao.checkList(
            listOf(
                TranslateCache(id = 667L, sourceText = "first", translatedText = "первый")
            )
        )

        repository.add(value = TranslateResponse(sourceText = "second", translatedText = "второй"))
        translatesDao.checkList(
            listOf(
                TranslateCache(id = 667L, sourceText = "first", translatedText = "первый"),
                TranslateCache(id = 668L, sourceText = "second", translatedText = "второй")
            )
        )

        repository.delete(id = 667L)
        translatesDao.checkList(
            listOf(
                TranslateCache(id = 668L, sourceText = "second", translatedText = "второй")
            )
        )
    }

    private interface FakeNow : Now {
        class Base(private var value : Long) : FakeNow {
            override fun nowMillis() : Long {
                return ++value
            }
        }
    }

    private  interface FakeTranslatesDao : TranslatesDao {
        fun checkList(expected : List<TranslateCache>)
        fun expectList(list : List<TranslateCache>)
        class Base : FakeTranslatesDao {
            private val list = mutableListOf<TranslateCache>()
            override fun checkList(expected: List<TranslateCache>) {
                assertEquals(expected, list)
            }
            override fun expectList(list: List<TranslateCache>) {
                this.list.addAll(list)
            }
            override fun list() : List<TranslateCache> = list
            override fun add(translate : TranslateCache) {
                list.add(translate)
            }
            override fun item(id : Long) : TranslateCache =
                list.find{it.id == id}!!
            override fun delete(id : Long) {
                list.remove(item(id))
            }
        }
    }
}