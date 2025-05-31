package com.example.dummyjsonapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyjsonapp.domain.model.ProductDetails
import com.example.dummyjsonapp.domain.useCases.GetProductDetailsUseCase
import com.example.dummyjsonapp.util.ErrorType
import com.example.dummyjsonapp.util.Resource
import com.example.dummyjsonapp.util.StringMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class ProductDetailsUiState(
    val productDetails: ProductDetails? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel()
{
    private val productId = savedStateHandle.get<Int>("productId")

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState : StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        if (productId != null) {
            loadProductDetails(productId)
        } else {
            _uiState.value = _uiState.value.copy(
                error = StringMapper(context).getErrorString(ErrorType.MissingProductId)
            )
        }
    }

    private fun loadProductDetails(productId: Int) {

        getProductDetailsUseCase.invoke(productId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        productDetails = result.data
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = StringMapper(context).getErrorString(result.error)
                    )
                }

            }

            }.launchIn(viewModelScope)

        }
}