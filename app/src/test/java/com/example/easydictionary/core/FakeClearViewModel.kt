package com.example.easydictionary.core

import androidx.lifecycle.ViewModel
import org.junit.Assert

interface FakeClearViewModel : ClearViewModel {
    fun checkClearCalled(expected : Class<out ViewModel>)

    class Base() : FakeClearViewModel {
        private lateinit var actual : Class<out ViewModel>
        override fun checkClearCalled(expected: Class<out ViewModel>) {
            Assert.assertEquals(expected, actual)
        }
        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            actual = viewModelClass
        }
    }
}