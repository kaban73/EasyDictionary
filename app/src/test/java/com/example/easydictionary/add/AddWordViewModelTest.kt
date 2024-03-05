package com.example.easydictionary.add

import androidx.lifecycle.LiveData
import com.example.easydictionary.core.FakeClearViewModel
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.data.translateRepository.LoadResult
import com.example.easydictionary.data.translateRepository.TranslateRepository
import com.example.easydictionary.data.translateRepository.TranslateResponse
import com.example.easydictionary.list.TranslateUi
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddWordViewModelTest {
    private lateinit var order : Order
    private lateinit var translateRepository: FakeTranslateRepository
    private lateinit var roomRepository : FakeRoomRepository
    private lateinit var listLiveDataWrapper : FakeAddListLiveDataWrapper
    private lateinit var addTranslateListLiveDataWrapper : FakeAddTranslateLiveDataWrapper
    private lateinit var clear: FakeClearViewModel
    private lateinit var viewModel : AddViewModel

    @Before
    fun setUp() {
        order = Order()
        translateRepository = FakeTranslateRepository.Base(order)
        roomRepository = FakeRoomRepository.Base(order)
        listLiveDataWrapper = FakeAddListLiveDataWrapper.Base(order)
        addTranslateListLiveDataWrapper = FakeAddTranslateLiveDataWrapper.Base(order)
        clear = FakeClearViewModel.Base(order)

        viewModel = AddViewModel(
            translateRepository = translateRepository,
            roomRepository = roomRepository,
            listLiveDataWrapper = listLiveDataWrapper,
            addTranslateLiveDataWrapper = addTranslateListLiveDataWrapper,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_successfully_add() {
        translateRepository.expectResult(LoadResult.Success(TranslateResponse(
            sourceText = "hello",
            translatedText = "Привет"
        )))

        viewModel.add(
            sourceLang = "en",
            targetLang = "ru",
            text = "hello"
        )

        translateRepository.checkLoadCalledTimes(1)

        roomRepository.checkAddCalled(
            TranslateResponse(
            sourceText = "hello",
            translatedText = "Привет"
        )
        )
        listLiveDataWrapper.checkAddCalled(
            TranslateUi(
                id = 10L,
                sourceText = "hello",
                translatedText = "Привет"
        ))
        addTranslateListLiveDataWrapper.checkAddCalled(UiState.ShowSuccess("Successfully added"))

        order.checkCallsList(listOf(TRANSLATE_REPOSITORY_LOAD, ROOM_REPOSITORY_ADD, LIST_LIVEDATA_ADD, ADD_TRANSLATE_LIVEDATA_ADD))
    }

    @Test
    fun test_error_noInternet() {
        translateRepository.expectResult(LoadResult.Error(noConnection = true))

        viewModel.add(
            sourceLang = "en",
            targetLang = "ru",
            text = "hello"
        )

        translateRepository.checkLoadCalledTimes(1)

        addTranslateListLiveDataWrapper.checkAddCalled(UiState.ShowError("No internet connection"))

        order.checkCallsList(listOf(TRANSLATE_REPOSITORY_LOAD,ADD_TRANSLATE_LIVEDATA_ADD))
    }

    @Test
    fun test_error_other() {
        translateRepository.expectResult(LoadResult.Error(noConnection = false))

        viewModel.add(
            sourceLang = "en",
            targetLang = "ru",
            text = "hello"
        )

        translateRepository.checkLoadCalledTimes(1)

        addTranslateListLiveDataWrapper.checkAddCalled(UiState.ShowError("Something went wrong"))

        order.checkCallsList(listOf(TRANSLATE_REPOSITORY_LOAD,ADD_TRANSLATE_LIVEDATA_ADD))
    }

    @Test
    fun comeback() {
        viewModel.comeback()
        clear.checkClearCalled(AddViewModel::class.java)
        order.checkCallsList(listOf(FakeClearViewModel.CLEAR))
    }

    class Order {
        private val list = mutableListOf<String>()

        fun add(value: String) {
            list.add(value)
        }

        fun checkCallsList(expected: List<String>) {
            assertEquals(expected, list)
        }
    }
    private interface FakeAddListLiveDataWrapper : ListLiveDataWrapper.Add {
        fun checkAddCalled(expected: TranslateUi)
        class Base(
            private val order : Order
        ) : FakeAddListLiveDataWrapper {
            private var actual = TranslateUi(0L, "","")
            override fun checkAddCalled(expected: TranslateUi) {
                assertEquals(expected, actual)
            }

            override fun add(value: TranslateUi) {
                order.add(LIST_LIVEDATA_ADD)
                actual = value
            }

        }
    }
    private interface FakeAddTranslateLiveDataWrapper : AddTranslateLiveDataWrapper.Mutable {
        fun checkAddCalled(expected: UiState)
        class Base(
            private val order : Order
        ) : FakeAddTranslateLiveDataWrapper {
            private lateinit var actual : UiState
            override fun checkAddCalled(expected: UiState) {
                assertEquals(expected, actual)
            }

            override fun liveData(): LiveData<UiState> {
                throw IllegalStateException("don't use in test")
            }

            override fun update(value: UiState) {
                order.add(ADD_TRANSLATE_LIVEDATA_ADD)
                actual = value
            }

        }
    }
    private interface FakeTranslateRepository : TranslateRepository {
        fun expectResult(result : LoadResult)
        fun checkLoadCalledTimes(times: Int)
        class Base(
            private val order: Order
        ) : FakeTranslateRepository {
            private lateinit var result : LoadResult
            private var actualCalledTimes : Int = 0
            override fun expectResult(result: LoadResult) {
                this.result = result
            }

            override fun checkLoadCalledTimes(times: Int) {
                assertEquals(times, actualCalledTimes)
            }

            override suspend fun load(
                sourceLang: String,
                targetLang: String,
                text: String
            ): LoadResult {
                order.add(TRANSLATE_REPOSITORY_LOAD)
                actualCalledTimes++
                return result
            }

        }
    }
    private interface FakeRoomRepository : RoomRepository.Add {
        fun checkAddCalled(expected : TranslateResponse)

        class Base(
            private val order: Order
        ) : FakeRoomRepository {
            private var actual = TranslateResponse("", "")
            private var id = 10L
            override fun checkAddCalled(expected: TranslateResponse) {
                assertEquals(expected, actual)
            }

            override fun add(value: TranslateResponse): Long {
                order.add(ROOM_REPOSITORY_ADD)
                actual = value
                return id++
            }

        }
    }
}

private const val LIST_LIVEDATA_ADD = "listLiveData#add"
private const val ADD_TRANSLATE_LIVEDATA_ADD = "addTranslateLiveData#add"
private const val ROOM_REPOSITORY_ADD = "roomRepository#add"
private const val TRANSLATE_REPOSITORY_LOAD = "translateRepository#load"