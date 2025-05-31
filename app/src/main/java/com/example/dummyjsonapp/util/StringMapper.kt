package com.example.dummyjsonapp.util

import android.content.Context
import com.example.dummyjsonapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getErrorString(error: ErrorType): String {
        return when (error) {
            is ErrorType.NetworkError -> context.getString(R.string.error_network)
            is ErrorType.ServerError -> context.getString(R.string.error_server)
            is ErrorType.DatabaseError -> context.getString(R.string.error_database)
            else -> context.getString(R.string.error_unknown)
        }
    }
}