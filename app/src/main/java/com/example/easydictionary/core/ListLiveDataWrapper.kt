package com.example.easydictionary.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easydictionary.list.TranslateUi

interface ListLiveDataWrapper {
    interface Read {
        fun liveData() : LiveData<List<TranslateUi>>
    }
    interface Add {
        fun add(value : TranslateUi)
    }
    interface Update {
        fun update(value : List<TranslateUi>)
    }
    interface Delete {
        fun delete(value: TranslateUi)
    }
    interface Mutable : Read, Update, Add
    interface All : Mutable, Delete
    class Base(
        private val liveData : MutableLiveData<List<TranslateUi>> = MutableLiveData()
    ) : All {
        override fun liveData(): LiveData<List<TranslateUi>> = liveData
        override fun add(value: TranslateUi) {
            val list = liveData.value?.toMutableList() ?: ArrayList()
            list.add(value)
            update(list)
        }

        override fun update(value: List<TranslateUi>) {
            liveData.value = value
        }

        override fun delete(value: TranslateUi) {
            val list = liveData.value?.toMutableList() ?: ArrayList()
            list.remove(value)
            update(list)
        }
    }
}