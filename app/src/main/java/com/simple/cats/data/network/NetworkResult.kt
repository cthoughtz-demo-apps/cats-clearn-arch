package com.simple.cats.data.network

import com.simple.cats.presentation.util.CatResult

sealed class NetworkResult<T>{
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Failure<T>(val errorMessage: String): NetworkResult<T>()
}

fun <T> NetworkResult<T>.toResult() = when (this) {
    is NetworkResult.Failure -> CatResult.Failure(this.errorMessage)
    is NetworkResult.Success -> CatResult.Success(this.data)
}