package com.sandesh.fintrack

import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TransactionsViewModel
    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        repository = mock()
        whenever(repository.observeTransactions())
            .thenReturn(MutableStateFlow(emptyList()))

        viewModel = TransactionsViewModel(repository)
    }

    @Test
    fun `sanity test`() = runTest {
        advanceUntilIdle()
        assert(true)
    }
}
