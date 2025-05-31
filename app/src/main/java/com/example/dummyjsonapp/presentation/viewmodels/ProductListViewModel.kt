package com.example.dummyjsonapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.domain.useCases.GetFilteredProductsUseCase
import com.example.dummyjsonapp.util.AppConstants
import com.example.dummyjsonapp.util.Resource
import com.example.dummyjsonapp.util.StringMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class ProductListUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchText: String = ""
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getFilteredProductsUseCase: GetFilteredProductsUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val _products = MutableStateFlow<Flow<PagingData<Product>>>(emptyFlow())
    val products: StateFlow<Flow<PagingData<Product>>> = _products.asStateFlow()

    // to allow search debouncing
    private val queryFlow = MutableStateFlow("")

    init {
        observeQueryChanges()
        getFilteredProducts(query = AppConstants.EMPTY_STRING)
    }

    fun updateSearchQuery(query: String) {
        queryFlow.value = query
        _uiState.value = _uiState.value.copy(
            searchText = query
        )
    }

    private fun getFilteredProducts(query: String) {
        getFilteredProductsUseCase.invoke(query = query)
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = true,
                            error = AppConstants.EMPTY_STRING
                        )
                    }

                    is Resource.Success -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = AppConstants.EMPTY_STRING,
                        )

                        //cache flow data in the view model scope -> Flow<PagingData<T>> only
                        // supports a single collector
                        _products.value = flowOf(result.data).cachedIn(viewModelScope)
                    }

                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = StringMapper(context = context).getErrorString(result.error)
                        )
                    }

                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    fun observeQueryChanges() {
        queryFlow.debounce(500) // wait 0.5s before emitting new value, avoid multiple requests
            .distinctUntilChanged()
            .onEach { query ->
                getFilteredProducts(query = query)
            }.launchIn(viewModelScope)

    }
}