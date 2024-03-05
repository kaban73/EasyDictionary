package com.example.easydictionary.data.translateRepository

import java.net.UnknownHostException

interface TranslateRepository {
    suspend fun load(
        sourceLang: String,
        targetLang: String,
        text: String
    ): LoadResult
    class Base(
        private val service: TranslateService
    ) : TranslateRepository {
        override suspend fun load(
            sourceLang: String,
            targetLang: String,
            text: String
        ): LoadResult =
            try {
                val response = service.fetch(sourceLang,targetLang,text)
                LoadResult.Success(response)
            } catch (e : Exception) {
                LoadResult.Error(e is UnknownHostException)
            }
    }
}