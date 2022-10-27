package com.test.newsapp.data.utils

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): Result<T>(data = data)
    class Error<T>(data: T? = null, message: String? = null): Result<T>(data, message)
    class Loading<T>(data: T? = null): Result<T>(data)
}