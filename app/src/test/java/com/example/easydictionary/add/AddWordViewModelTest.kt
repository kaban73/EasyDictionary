package com.example.easydictionary.add

import com.example.easydictionary.data.roomRepository.RoomRepository
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class AddWordViewModelTest {

    private interface FakeAddRepository : RoomRepository.Add {
        fun checkAddCalled(expected : )
    }
}