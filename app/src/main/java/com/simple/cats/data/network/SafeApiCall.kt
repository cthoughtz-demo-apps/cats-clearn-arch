package com.simple.cats.data.network

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
) : NetworkResult<T> {
    return try {
        apiCall().let {
            NetworkResult.Success(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkResult.Failure(e.message ?: "Something went wrong")
    }
}