package com.simple.cats.data.network

import com.simple.cats.domain.model.BreedFactsResponse
import com.simple.cats.domain.model.BreedSearchResponse
import com.simple.cats.domain.model.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/images/search")
    suspend fun getImages(): ImageSearchResponse

    @GET("/v1/breeds/search")
    suspend fun getBreed(@Query("q") q: String): BreedSearchResponse

        @Headers(
        "Content-Type: application/json",
        "x-api-key: live_dkwwZk2UvF4BABv06PmgzrwFZrM2YoCuxhllGgci5HQQF0cPzrpSvgsYi7WeO5rq",
    )
    @GET("/v1/breeds/{breed_id}/facts")
    suspend fun getFacts(
        @Path("breed_id") breedId: Int
    ): BreedFactsResponse
}