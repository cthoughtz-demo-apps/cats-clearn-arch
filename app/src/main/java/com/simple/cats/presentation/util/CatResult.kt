package com.simple.cats.presentation.util

sealed class CatResult<out T> {
    data object Loading: CatResult<Nothing>()
    data class Success<T>(val data: T): CatResult<T>()
    data class Failure<T>(val errorMessage: String): CatResult<T>()
    data object None: CatResult<Nothing>()
}