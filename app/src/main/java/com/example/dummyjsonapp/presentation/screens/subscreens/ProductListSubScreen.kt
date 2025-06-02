package com.example.dummyjsonapp.presentation.screens.subscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.presentation.composables.ProductList
import com.example.dummyjsonapp.presentation.misc.FullScreenLoadingOverlay
import com.example.dummyjsonapp.presentation.viewmodels.ProductListUiState
import com.example.dummyjsonapp.presentation.viewmodels.ProductListViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ProductListSubScreen(
    viewModel: ProductListViewModel,
    onClickedCard: (Int) -> Unit
) {
    //Collecting state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val pagingData by viewModel.products.collectAsState()

    //workaround to avoid breaking the preview (it happens when a viewmodel is passed as parameter)
    ProductListSubScreenContent(
        uiState,
        onSearchTextChange = { viewModel.updateSearchQuery(it) },
        onClickedCard = onClickedCard,
        pagingData
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListSubScreenContent(
    uiState: ProductListUiState,
    onSearchTextChange: (String) -> Unit = {},
    onClickedCard: (Int) -> Unit,
    pagingData : Flow<PagingData<Product>>
) {

    val lazyProducts = remember(pagingData) { pagingData }.collectAsLazyPagingItems()

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = uiState.searchText,
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text(stringResource(R.string.search_placeholder)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        onQueryChange = onSearchTextChange
                    )
                },
                expanded = false,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                content = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProductList(
                modifier = Modifier.padding(horizontal = 16.dp),
                productList = lazyProducts,
                onClickedCard = onClickedCard
            )

        }

        if (uiState.isLoading) {
            FullScreenLoadingOverlay(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
