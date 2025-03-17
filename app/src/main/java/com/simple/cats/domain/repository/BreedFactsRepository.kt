package com.simple.cats.domain.repository

import com.simple.cats.data.network.ApiService
import com.simple.cats.data.network.safeApiCall
import com.simple.cats.data.network.toResult
import com.simple.cats.domain.model.BreedFactsResponse
import com.simple.cats.presentation.util.CatResult
import javax.inject.Inject

interface BreedFactsRepository {
    suspend fun getFacts(breedId: String) : CatResult<BreedFactsResponse>
}

class BreedFactsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BreedFactsRepository {
    override suspend fun getFacts(breedId: String): CatResult<BreedFactsResponse> {
        return safeApiCall { apiService.getFacts(breedId) }.toResult()
    }
}