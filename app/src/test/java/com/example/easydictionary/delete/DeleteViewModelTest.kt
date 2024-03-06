package com.example.easydictionary.delete

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.easydictionary.add.AddWordViewModelTest
import com.example.easydictionary.core.FakeClearViewModel
import com.example.easydictionary.core.FakeClearViewModel.Companion.CLEAR
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.delete.DeleteViewModelTest.FakeRoomDeleteRepository.Companion.REPOSITORY_DELETE
import com.example.easydictionary.list.FakeListLiveDataWrapper
import com.example.easydictionary.list.FakeListLiveDataWrapper.Companion.LIVE_DATA_DELETE
import com.example.easydictionary.list.Translate
import com.example.easydictionary.list.TranslateUi
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class DeleteViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var order : AddWordViewModelTest.Order
    private lateinit var listLiveDataWrapper: FakeListLiveDataWrapper
    private lateinit var deleteLiveDataWrapper : DeleteLiveDataWrapper.Mutable
    private lateinit var repository: FakeRoomDeleteRepository
    private lateinit var clear : FakeClearViewModel
    private lateinit var viewModel : DeleteViewModel

    @Before
    fun setUp() {
        order = AddWordViewModelTest.Order()
        listLiveDataWrapper = FakeListLiveDataWrapper.Base(order)
        deleteLiveDataWrapper = DeleteLiveDataWrapper.Base()
        repository = FakeRoomDeleteRepository.Base(order)
        clear = FakeClearViewModel.Base(order)
        viewModel = DeleteViewModel(
            repository = repository,
            listLiveDataWrapper = listLiveDataWrapper,
            deleteLiveDataWrapper = deleteLiveDataWrapper,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        viewModel.init(itemId = 1L)
        assertEquals(Pair("1", "1"), viewModel.liveData().value)
    }

    @Test
    fun test_delete() {
        listLiveDataWrapper.update(listOf(
            TranslateUi(0L, "0", "0"),
            TranslateUi(1L, "run", "бегать")
        ))
        viewModel.init(itemId = 0L)

        viewModel.delete(itemId = 0L)
        repository.checkDeleteCalled(0L)
        listLiveDataWrapper.checkUpdateCalls(listOf(TranslateUi(1L, "run", "бегать")))
        clear.checkClearCalled(DeleteViewModel::class.java)
        order.checkCallsList(listOf(REPOSITORY_DELETE, LIVE_DATA_DELETE, CLEAR))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        clear.checkClearCalled(DeleteViewModel::class.java)
    }

    private interface FakeRoomDeleteRepository : RoomRepository.Delete {
        companion object {
            const val REPOSITORY_DELETE = "Repository.Delete#delete"
        }
        fun checkDeleteCalled(id : Long)
        class Base(
            private val order : AddWordViewModelTest.Order = AddWordViewModelTest.Order()
        ) : FakeRoomDeleteRepository {
            private var actualId : Long = -1L
            override fun checkDeleteCalled(id: Long) {
                assertEquals(id, actualId)
            }

            override fun delete(id: Long) {
                order.add(REPOSITORY_DELETE)
                actualId = id
            }

            override fun item(id: Long): Translate {
                return Translate(id, id.toString(), id.toString())
            }

        }
    }
}