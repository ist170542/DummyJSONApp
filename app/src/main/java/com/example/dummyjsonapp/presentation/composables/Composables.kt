package com.example.dummyjsonapp.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.domain.model.Product

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    productList: LazyPagingItems<Product>,
    onClickedCard: (Int) -> Unit
) {
        LazyVerticalGrid(
//            columns = GridCells.Adaptive(minSize = 128.dp),
            columns = GridCells.Fixed(1),
            modifier = modifier.fillMaxSize()
        ) {
            items(productList.itemCount) { index ->

                productList[index]?.let {
                    ProductGridItem(
                        product = it,
                        onClickedCard = onClickedCard
                    )
                }
            }
        }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductGridItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClickedCard: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                onClickedCard(product.id)
            }, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = product.rating.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp)
                )

                Icon(
                    painter = painterResource(id = product.getRatingIcon()),
                    contentDescription = "Rating Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }


            // Main column for image + text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    model = product.thumbnail,
                    loading = placeholder(R.drawable.ic_placeholder_product),
                    failure = placeholder(R.drawable.ic_placeholder_product),
                    contentScale = ContentScale.Crop,
                    contentDescription = product.title
                )

                // Breed name below the image
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }

        }
    }
}