package com.example.dummyjsonapp.util

sealed class ErrorType(val code: String) {
    // General errors
    object NetworkError : ErrorType("error_network")
    object ServerError : ErrorType("error_server")
    object DatabaseError : ErrorType("error_database")
    object UnknownError : ErrorType("error_unknown")

    // Specific business errors
    object MissingProductId : ErrorType("error_missing_product_id")
}