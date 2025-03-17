package com.simple.cats.domain.repository

import com.simple.cats.data.network.ApiService
import com.simple.cats.data.network.safeApiCall
import com.simple.cats.data.network.toResult
import com.simple.cats.domain.model.ImageSearchResponse
import com.simple.cats.presentation.util.CatResult
import javax.inject.Inject

interface ImageSearchRepository {
    suspend fun getImages(): CatResult<ImageSearchResponse>
}

class ImageSearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageSearchRepository {
    override suspend fun getImages(): CatResult<ImageSearchResponse> {
        return safeApiCall { apiService.getImages() }.toResult()
    }
}