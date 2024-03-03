package com.example.easydictionary.data.roomRepository

import com.example.easydictionary.list.Translate

interface RoomRepository {
    interface Read{
        fun list() : List<Translate>
    }
    interface Add {
        fun add(value: String) : Long
    }
    interface Item {
        fun item(id : Long) : Translate
    }
    interface Delete : Item {
        fun delete(id : Long)
    }
    interface Mutable : Read, Add
    interface All : Mutable, Delete
    class Base(
        private val translatesDao : TranslatesDao,
        private val now : Now
    ) : All {
        override fun list(): List<Translate> {
            val list = mutableListOf<Translate>()
            translatesDao.list().map {
                list.add(Translate(it.id, it.sourceText, it.translatedText))
            }
            return list
        }

        override fun add(value: String)  : Long {
            val id = now.nowMillis()
            translatesDao.add(TranslateCache(id, value, value))
            return id
        }

        override fun delete(id: Long) {
            translatesDao.delete(id)
        }

        override fun item(id: Long): Translate {
            return translatesDao.item(id).let {
                Translate(it.id, it.sourceText, it.translatedText)
            }
        }
    }
}