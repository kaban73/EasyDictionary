package com.example.easydictionary.repository

import com.example.easydictionary.data.translateRepository.LoadResult
import com.example.easydictionary.data.translateRepository.TranslateRepository
import com.example.easydictionary.data.translateRepository.TranslateResponse
import com.example.easydictionary.data.translateRepository.TranslateService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception
import java.net.UnknownHostException

class TranslateRepositoryTest {

    @Test
    fun test_success() = runBlocking {
        val service = FakeTranslateService.Base()
        service.expectSuccess()
        val repository = TranslateRepository.Base(
            service = service
            )
        val sourceLang = "ru"
        val destinationLang = "en"
        val text = "привет"
        val actual = repository.load(sourceLang,destinationLang,text)
        val expected = LoadResult.Success(data = TranslateResponse(
            sourceText = "привет",
            translatedText = "hello"
        )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_no_connection() = runBlocking {
        val service = FakeTranslateService.Base()
        service.expectException(UnknownHostException())

        val repository = TranslateRepository.Base(
            service = service
        )
        val sourceLang = "ru"
        val destinationLang = "en"
        val text = "привет"
        val actual = repository.load(sourceLang,destinationLang,text)
        val expected = LoadResult.Error(noConnection = true)
        assertEquals(expected, actual)
    }

    @Test
    fun test_other_exception() = runBlocking {
        val service = FakeTranslateService.Base()
        service.expectException(IllegalStateException())

        val repository = TranslateRepository.Base(
            service = service
        )
        val sourceLang = "ru"
        val destinationLang = "en"
        val text = "привет"
        val actual = repository.load(sourceLang,destinationLang,text)
        val expected = LoadResult.Error(noConnection = false)
        assertEquals(expected, actual)
    }

    private interface FakeTranslateService : TranslateService {
        fun expectSuccess()
        fun expectException(exception: Exception)
        class Base : FakeTranslateService {
            private val map = mutableMapOf<String, TranslateResponse>()
            init {
                map["привет"] = TranslateResponse(
                    sourceText = "привет",
                    translatedText = "hello"
                )
                map["jump"] = TranslateResponse(
                    sourceText = "jump",
                    translatedText = "прыгать"
                )
            }

            private var expectSuccessResult : Boolean = false
            private lateinit var exceptionToThrow : Exception

            override fun expectSuccess() {
                expectSuccessResult = true
            }

            override fun expectException(exception: Exception) {
                exceptionToThrow = exception
            }
            override suspend fun fetch(sourceLang : String, targetLang : String, word : String) : TranslateResponse {
                if (expectSuccessResult)
                    return map[word]!!
                else
                    throw exceptionToThrow
            }
        }
    }
}