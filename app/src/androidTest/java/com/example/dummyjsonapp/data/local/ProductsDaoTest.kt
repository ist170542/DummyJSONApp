package com.example.dummyjsonapp.data.local

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dummyjsonapp.data.local.database.AppDatabase
import com.example.dummyjsonapp.data.local.database.ProductsDao
import com.example.dummyjsonapp.data.local.entities.ProductEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductsDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ProductsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.productsDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndGetAllProductsPaged_returnsAll() = runTest {
        val products = listOf(
            ProductEntity(
                id = 1,
                title  = "Phone",
                description = "Smartphone",
                rating = 1.0f,
                thumbnail = "dummy_thumbnail_url"
            ),
            ProductEntity(
                id = 2,
                title ="Laptop",
                description = "Gaming laptop",
                rating = 1.5f,
                thumbnail = "dummy_thumbnail_url2"
            )
        )
        dao.insertProducts(products)

        val pagingSource = dao.getAllProductsPaged()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        ) as PagingSource.LoadResult.Page

        assertEquals(2, result.data.size)
    }

    @Test
    fun searchProducts_returnsMatchingResults() = runTest {
        val products = listOf(
            ProductEntity(
                id = 1,
                title  = "Phone",
                description = "Smartphone",
                rating = 1.0f,
                thumbnail = "dummy_thumbnail_url"
            ),
            ProductEntity(
                id = 2,
                title ="Laptop",
                description = "Gaming laptop",
                rating = 1.5f,
                thumbnail = "dummy_thumbnail_url2"
            )
        )
        dao.insertProducts(products)

        val pagingSource = dao.searchProducts("Phone")
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        ) as PagingSource.LoadResult.Page

        assertEquals(1, result.data.size)
        assertEquals("Phone", result.data[0].title)
    }

    @Test
    fun searchProducts_returnsEmptyWhenNoMatch() = runTest {
        val products = listOf(
            ProductEntity(
                id = 1,
                title  = "Phone",
                description = "Smartphone",
                rating = 1.0f,
                thumbnail = "dummy_thumbnail_url"
            )
        )
        dao.insertProducts(products)

        val pagingSource = dao.searchProducts("Nonexistent")
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        ) as PagingSource.LoadResult.Page

        assertTrue(result.data.isEmpty())
    }

    @Test
    fun insertProduct_replacesOnConflict() = runTest {
        val original = ProductEntity(
            id = 1,
            title  = "Phone",
            description = "Smartphone",
            rating = 1.0f,
            thumbnail = "dummy_thumbnail_url"
        )

        val updated = ProductEntity(
            id = 1,
            title  = "Phone",
            description = "Updated Description",
            rating = 1.2f,
            thumbnail = "dummy_thumbnail_url"
        )

        dao.insertProducts(listOf(original))
        dao.insertProducts(listOf(updated))

        val pagingSource = dao.getAllProductsPaged()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        ) as PagingSource.LoadResult.Page

        assertEquals(1, result.data.size)
        assertEquals("Updated Description", result.data[0].description)
    }
}