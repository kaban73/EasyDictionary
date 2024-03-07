package com.example.easydictionary.core

import androidx.lifecycle.ViewModel
import com.example.easydictionary.add.Order
import org.junit.Assert

interface FakeClearViewModel : ClearViewModel {

    companion object {
        const val CLEAR = "clearAll"
    }
    fun checkClearCalled(expected : Class<out ViewModel>)

    class Base(
        private val order: Order = Order()
    ) : FakeClearViewModel {
        private lateinit var actual : Class<out ViewModel>
        override fun checkClearCalled(expected: Class<out ViewModel>) {
            Assert.assertEquals(expected, actual)
        }
        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            order.add(CLEAR)
            actual = viewModelClass
        }
    }
}