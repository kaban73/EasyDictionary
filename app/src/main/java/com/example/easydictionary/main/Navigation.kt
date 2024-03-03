package com.example.easydictionary.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface Navigation {
    interface Read {
        fun liveData() : LiveData<Screen>
    }
    interface Update {
        fun update(value : Screen)
    }
    interface Mutable : Read, Update
    class Base(
        private val liveData: MutableLiveData<Screen> = MutableLiveData()
    ) : Mutable {
        override fun update(value: Screen) {
            liveData.value = value
        }

        override fun liveData(): LiveData<Screen> =
            liveData
    }
}