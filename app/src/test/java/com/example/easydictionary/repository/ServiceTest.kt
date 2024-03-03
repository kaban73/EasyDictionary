package com.example.easydictionary.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceTest {
    @Test
    fun test() = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ftapi.pythonanywhere.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service : TranslateService = retrofit.create(TranslateService::class.java)
        val actual =
            service.fetch(
                sourceLang = "ru",
                destinationLang = "en",
                text = "привет"
            )
        val expected = TranslateResponse(
            sourceText = "привет",
            translatedText = "hello"
        )
        assertEquals(expected, actual)
    }
}