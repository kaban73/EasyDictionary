package com.example.easydictionary.data.translateRepository

import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {
    @GET("/translate")
    suspend fun fetch(
        @Query("sl") sourceLang: String,
        @Query("dl") targetLang: String,
        @Query("text") text: String
    ): TranslateResponse
}