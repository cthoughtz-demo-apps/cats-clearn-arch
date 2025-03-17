package com.simple.cats.domain.usecase

import com.simple.cats.domain.repository.ImageSearchRepository
import javax.inject.Inject

class ImageSearchUseCase @Inject constructor(
    private val searchRepository: ImageSearchRepository
) {
    suspend fun imageSearch() = searchRepository.getImages()
}