package com.example.dummyjsonapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.domain.model.ProductDetails
import com.example.dummyjsonapp.presentation.misc.FullScreenLoadingOverlay
import com.example.dummyjsonapp.presentation.viewmodels.ProductDetailsUiState
import com.example.dummyjsonapp.presentation.viewmodels.ProductDetailsViewModel

// Composable for Product Details Screen
@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ProductDetailsScreenContent(uiState, onBackClick)
}

@Composable
fun ProductDetailsScreenContent(
    uiState: ProductDetailsUiState,
    onBackClick: () -> Unit = {}
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        if (uiState.error.isNotEmpty()) {
            snackbarHostState.showSnackbar(uiState.error)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            FullScreenLoadingOverlay(Modifier.fillMaxSize())
        } else if (uiState.error.isNotEmpty()) {
            Text(uiState.error)
        } else {
            uiState.productDetails?.let {
                ProductDetailsContent(uiState.productDetails, onBackClick)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ProductDetailsContent(
    product: ProductDetails,
    onBackClick: () -> Unit
) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.product_details_title)) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back_button_description))
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                GlideImage(
                    model = product.image,
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.drawable.ic_placeholder_product),
                    failure = placeholder(R.drawable.ic_placeholder_product),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp)
                        .padding(24.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Text(
                    product.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.tags.forEach {
                        SuggestionChip(onClick = {}, label = { Text(it) })
                    }
                }

                InfoRow(
                    icon = Icons.Default.ShoppingCart,
                    title = stringResource(R.string.price_label),
                    value = "$${product.price}"
                )

                InfoRow(
                    icon = Icons.Default.ThumbUp,
                    title = stringResource(R.string.discount_label),
                    value = "${product.discountPercentage}%"
                )

                InfoRow(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.stock_label),
                    value = product.stock.toString()
                )

                InfoRow(
                    icon = Icons.Default.Star,
                    title = stringResource(R.string.rating_label),
                    value = product.rating.toString()
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    stringResource(R.string.description_label),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp),
                    lineHeight = 24.sp
                )

            }

        }



}

@Composable
fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}