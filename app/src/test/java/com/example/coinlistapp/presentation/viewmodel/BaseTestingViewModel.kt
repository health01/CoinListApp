package com.example.coinlistapp.presentation.viewmodel

import com.example.coinlistapp.util.DefaultCoroutineDispatchers
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before


open class BaseTestingViewModel {
    @MockK
    lateinit var mockDispatchers: DefaultCoroutineDispatchers
    val testDispatcher = StandardTestDispatcher()
    private fun setupDispatcher() {
        every { mockDispatchers.main() } returns testDispatcher
        every { mockDispatchers.default() } returns testDispatcher
        every { mockDispatchers.io() } returns testDispatcher
    }

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
        setupDispatcher()
    }

    @After
    open fun tearDown() {

    }
}