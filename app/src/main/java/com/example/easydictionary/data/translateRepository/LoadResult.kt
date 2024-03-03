package com.example.easydictionary.data.translateRepository

interface LoadResult {
    data class Success(private val data : TranslateResponse) : LoadResult {

    }
    data class Error(private val noConnection : Boolean) : LoadResult {

    }
}