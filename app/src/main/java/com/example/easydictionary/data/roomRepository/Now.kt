package com.example.easydictionary.data.roomRepository

interface Now {
    fun nowMillis() : Long
    class Base : Now {
        override fun nowMillis(): Long =
            System.currentTimeMillis()
    }
}