package com.example.easydictionary.data.translateRepository

import java.net.UnknownHostException

interface TranslateRepository {
    suspend fun load(
        sourceLang: String,
        targetLang: String,
        word: String
    ): LoadResult
    class Base(
        private val service: TranslateService
    ) : TranslateRepository {
        override suspend fun load(
            sourceLang: String,
            targetLang: String,
            word: String
        ): LoadResult =
            try {
                val response = service.fetch(sourceLang,targetLang,word)
                LoadResult.Success(response)
            } catch (e : Exception) {
                LoadResult.Error(e is UnknownHostException)
            }
    }
}