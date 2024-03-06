package com.example.easydictionary.list

import androidx.lifecycle.LiveData
import com.example.easydictionary.add.AddWordViewModelTest
import com.example.easydictionary.core.ListLiveDataWrapper
import org.junit.Assert.assertEquals

interface FakeListLiveDataWrapper : ListLiveDataWrapper.All {

    companion object {
        const val LIVE_DATA_DELETE = "ListLiveDataWrapper#delete"
    }

    fun checkUpdateCalls(expected : List<TranslateUi>)
    class Base(
        private val order : AddWordViewModelTest.Order = AddWordViewModelTest.Order()
    ) : FakeListLiveDataWrapper {
        private val actualUpdateCalls = mutableListOf<TranslateUi>()
        private var id = 0L
        override fun checkUpdateCalls(expected: List<TranslateUi>) {
            assertEquals(expected, actualUpdateCalls)
        }

        override fun liveData(): LiveData<List<TranslateUi>> {
            throw IllegalStateException("not used in test")
        }

        override fun update(value: List<TranslateUi>) {
            actualUpdateCalls.clear()
            actualUpdateCalls.addAll(value)
        }

        override fun add(value: TranslateUi) {
            actualUpdateCalls.add(TranslateUi(id, value.sourceText, value.translatedText))
        }

        override fun delete(value: TranslateUi) {
            order.add(LIVE_DATA_DELETE)
            actualUpdateCalls.remove(value)
        }

    }
}