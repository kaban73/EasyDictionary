package com.example.easydictionary.core

import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun <T : ViewModel> clear(viewModelClass: Class<T>)
}