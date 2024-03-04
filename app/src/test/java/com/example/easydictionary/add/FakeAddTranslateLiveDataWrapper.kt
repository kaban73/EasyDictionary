package com.example.easydictionary.add

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals

interface FakeAddTranslateLiveDataWrapper : AddTranslateLiveDataWrapper.Mutable {
    fun checkUpdateCalls(expected : List<UiState>)
    class Base : FakeAddTranslateLiveDataWrapper {
        private val actualCallsList = mutableListOf<UiState>()
        override fun checkUpdateCalls(expected: List<UiState>) {
            assertEquals(expected, actualCallsList)
        }

        override fun liveData(): LiveData<UiState> {
            throw IllegalStateException("not used in test")
        }

        override fun update(value: UiState) {
            actualCallsList.add(value)
        }
    }
}