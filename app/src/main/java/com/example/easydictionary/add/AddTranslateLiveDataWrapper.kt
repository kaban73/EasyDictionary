package com.example.easydictionary.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easydictionary.core.SingleLiveEvent

interface AddTranslateLiveDataWrapper {
    interface Read {
        fun liveData() : LiveData<UiState>
    }
    interface Update {
        fun update(value: UiState)
    }
    interface Mutable : Read, Update
    class Base(
        private val liveData : MutableLiveData<UiState> = SingleLiveEvent()
    ) : Mutable {
        override fun liveData(): LiveData<UiState> = liveData

        override fun update(value: UiState) {
            liveData.value = value
        }
    }
}