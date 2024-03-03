package com.example.easydictionary.repository

import com.example.easydictionary.data.translateRepository.LoadResult
import com.example.easydictionary.data.translateRepository.TranslateResponse
import org.junit.Test

class TranslateResultTest {
    @Test
    fun test_success() {
        val result = LoadResult.Success(data = TranslateResponse(
            sourceText = "hello",
            translatedText = "привет"
        )
        )
        val liveDataWrapper = FakeTranslateLiveDataWrapper.Base()
        val liveDataWrapperUpdate : TranslateLiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "Successfully added")))
    }

    @Test
    fun test_no_connection() {
        val result = LoadResult.Error(noConnection = true)
        val liveDataWrapper = FakeTranslateLiveDataWrapper.Base()
        val liveDataWrapperUpdate : TranslateLiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "No internet connection")))
    }

    @Test
    fun test_other() {
        val result = LoadResult.Error(noConnection = false)
        val liveDataWrapper = FakeTranslateLiveDataWrapper.Base()
        val liveDataWrapperUpdate : TranslateLiveDataWrapper.Update = liveDataWrapper
        result.show(updateLiveData = liveDataWrapperUpdate)
        liveDataWrapper.checkUpdateCalls(listOf(UiState.ShowData(text = "Something went wrong")))
    }
}