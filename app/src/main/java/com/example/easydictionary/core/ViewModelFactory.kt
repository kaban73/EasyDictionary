package com.example.easydictionary.core

import androidx.lifecycle.ViewModel

interface ViewModelFactory : ProvideViewModel, ClearViewModel {
    class Base(
        private val provideViewModel: ProvideViewModel
    ) : ViewModelFactory {
        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            val viewModel = map[viewModelClass]
            return if (viewModel == null)
                provideViewModel.viewModel(viewModelClass).also {
                    map[viewModelClass] = it
                }
            else
                viewModel as T
        }

        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            map.remove(viewModelClass)
        }

    }
}