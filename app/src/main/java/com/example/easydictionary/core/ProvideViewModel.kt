package com.example.easydictionary.core

import androidx.lifecycle.ViewModel
import com.example.easydictionary.data.roomRepository.Core
import com.example.easydictionary.main.MainViewModel
import com.example.easydictionary.main.Navigation

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass : Class<T>) : T
    class Base(
        core : Core,
        private val clearViewModel: ClearViewModel
    ) : ProvideViewModel {
        private val navigation = Navigation.Base()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T = when(viewModelClass) {
            MainViewModel::class.java -> MainViewModel(navigation)
            else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
        } as T
    }
}