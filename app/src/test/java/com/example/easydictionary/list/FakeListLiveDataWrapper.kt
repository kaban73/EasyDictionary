package com.example.easydictionary.list

import androidx.lifecycle.LiveData
import com.example.easydictionary.core.ListLiveDataWrapper
import org.junit.Assert.assertEquals

interface FakeListLiveDataWrapper : ListLiveDataWrapper.Mutable {
    fun checkUpdateCalls(expected : List<Translate>)
    class Base : FakeListLiveDataWrapper {
        private val actualUpdateCalls = mutableListOf<Translate>()
        private var id = 0L
        override fun checkUpdateCalls(expected: List<Translate>) {
            assertEquals(expected, actualUpdateCalls)
        }

        override fun liveData(): LiveData<List<Translate>> {
            throw IllegalStateException("not used in test")
        }

        override fun update(value: List<Translate>) {
            throw IllegalStateException("not used in test")
        }

        override fun add(value: Translate) {
            actualUpdateCalls.add(Translate(id, value.sourceText, value.translatedText))
        }

    }
}