package com.example.easydictionary.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easydictionary.list.Translate

interface ListLiveDataWrapper {
    interface Read {
        fun liveData() : LiveData<List<Translate>>
    }
    interface Add {
        fun add(value : Translate)
    }
    interface Update {
        fun update(value : List<Translate>)
    }
    interface Delete {
        fun delete(value: Translate)
    }
    interface Mutable : Read, Update, Add
    interface All : Mutable, Delete
    class Base(
        private val liveData : MutableLiveData<List<Translate>> = MutableLiveData()
    ) : All {
        override fun liveData(): LiveData<List<Translate>> = liveData
        override fun add(value: Translate) {
            val list = liveData.value?.toMutableList() ?: ArrayList()
            list.add(value)
            update(list)
        }

        override fun update(value: List<Translate>) {
            liveData.value = value
        }

        override fun delete(value: Translate) {
            val list = liveData.value?.toMutableList() ?: ArrayList()
            list.remove(value)
            update(list)
        }
    }
}