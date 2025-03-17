package com.simple.cats.domain.repository

import com.simple.cats.data.network.ApiService
import com.simple.cats.data.network.safeApiCall
import com.simple.cats.data.network.toResult
import com.simple.cats.domain.model.BreedSearchResponse
import com.simple.cats.presentation.util.CatResult
import javax.inject.Inject

interface BreedRepository {
    suspend fun getBreed(q: String): CatResult<BreedSearchResponse>
}

class BreedRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BreedRepository {
    override suspend fun getBreed(q: String): CatResult<BreedSearchResponse> {
        return safeApiCall { apiService.getBreed(q) }.toResult()
    }
}