package com.example.easydictionary.core

import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals

interface FakeProvideViewModel : ProvideViewModel {
    fun checkCalled(expected : List<Class<out ViewModel>>)
    fun <T : ViewModel> clear(viewModelClass : Class<T>)
    class Base : FakeProvideViewModel {
        private val list = mutableListOf<Class<out ViewModel>>()
        override fun checkCalled(expected: List<Class<out ViewModel>>) {
            assertEquals(expected, list)
        }

        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            list.remove(viewModelClass)
        }
        override fun <T : ViewModel> viewModel(viewModelClass : Class<T>) : T {
            list.add(viewModelClass)
            return viewModelClass.getDeclaredConstructor().newInstance()
        }
    }
}