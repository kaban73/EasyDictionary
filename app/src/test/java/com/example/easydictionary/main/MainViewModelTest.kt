package com.example.easydictionary.main

import com.example.easydictionary.list.ListScreen
import org.junit.Before
import org.junit.Test
class MainViewModelTest {
    private lateinit var navigation: FakeNavigation
    private lateinit var viewModel : MainViewModel

    @Before
    fun setup() {
        navigation = FakeNavigation.Base()
        val navigationMutable : Navigation.Mutable = navigation
        viewModel = MainViewModel(navigation = navigationMutable)
    }

    @Test
    fun test_first_run() {
        viewModel.init(firstRun = true)
        navigation.checkUpdateCalled(listOf(ListScreen))
    }

    @Test
    fun test_not_first_run() {
        viewModel.init(firstRun = false)
        navigation.checkUpdateCalled(listOf())
    }
}