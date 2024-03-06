package com.example.easydictionary.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface DeleteLiveDataWrapper {
    interface Read {
        fun liveData() : LiveData<Pair<String, String>>
    }
    interface Update {
        fun update(value : Pair<String, String>)
    }
    interface Mutable : Read, Update
    class Base(
        private val liveData: MutableLiveData<Pair<String, String>> = MutableLiveData()
    ) : Mutable {
        override fun liveData(): LiveData<Pair<String, String>> = liveData
        override fun update(value: Pair<String, String>) {
            liveData.value = value
        }
    }
}