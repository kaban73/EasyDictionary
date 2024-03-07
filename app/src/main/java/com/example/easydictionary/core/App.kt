package com.example.easydictionary.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.easydictionary.data.roomRepository.Core

class App : Application(), ProvideViewModel {
    lateinit var factory: ViewModelFactory
    private val clearViewModel : ClearViewModel = object : ClearViewModel {
        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            factory.clear(viewModelClass)
        }
    }
    override fun onCreate() {
        super.onCreate()
        val provideViewModel = ProvideViewModel.Base(
            Core(this),
            clearViewModel
        )
        factory = ViewModelFactory.Base(provideViewModel)
    }
    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)
}