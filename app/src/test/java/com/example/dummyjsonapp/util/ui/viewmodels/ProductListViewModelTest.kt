package com.example.dummyjsonapp.util.ui.viewmodels

import android.content.Context
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.domain.useCases.GetFilteredProductsUseCase
import com.example.dummyjsonapp.presentation.viewmodels.ProductListViewModel
import com.example.dummyjsonapp.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val getFilteredProductsUseCase = mockk<GetFilteredProductsUseCase>()
    private val context = mockk<Context>(relaxed = true)

    private lateinit var viewModel: ProductListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `updateSearchQuery updates uiState searchText`() = testScope.runTest {
        coEvery { getFilteredProductsUseCase.invoke(any()) } returns flowOf(Resource.Success(PagingData.empty()))
        viewModel = ProductListViewModel(getFilteredProductsUseCase, context)
        viewModel.updateSearchQuery("Laptop")
        assertEquals("Laptop", viewModel.uiState.value.searchText)
    }

    @Test
    fun `getFilteredProducts emits loading and then success with correct products`() = runTest {
        val pagingData = PagingData.from(listOf(Product(1, "Laptop", "", 1.0f, "dummy_thumb_url")))

        // Respond with loading + success for any query
        coEvery { getFilteredProductsUseCase.invoke(any()) } returns flowOf(
            Resource.Loading,
            Resource.Success(pagingData)
        )

        viewModel = ProductListViewModel(getFilteredProductsUseCase, context)

        // Trigger search first
        viewModel.updateSearchQuery("Laptop")

        // THEN start collecting
        viewModel.uiState.test {
            val first = awaitItem()  // should contain updated searchText
            assertEquals("Laptop", first.searchText)

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val success = awaitItem()
            assertFalse(success.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

}