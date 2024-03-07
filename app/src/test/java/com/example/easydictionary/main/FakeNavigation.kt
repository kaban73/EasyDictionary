package com.example.easydictionary.main

import androidx.lifecycle.LiveData
import com.example.easydictionary.add.Order
import org.junit.Assert.assertEquals
interface FakeNavigation : Navigation.Mutable {
    fun checkUpdateCalled(expected: List<Screen>)

    class Base(private val order : Order = Order()) : FakeNavigation {
        companion object {
            const val NAVIGATION = "navigation#Update"
        }
        private val callsList = mutableListOf<Screen>()

        override fun checkUpdateCalled(expected: List<Screen>) {
            assertEquals(expected, callsList)
        }

        override fun update(value : Screen) {
            order.add(NAVIGATION)
            callsList.add(value)
        }
        override fun liveData() : LiveData<Screen> {
            throw IllegalStateException("not used")
        }
    }
}