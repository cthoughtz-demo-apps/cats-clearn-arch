package com.simple.cats.domain.usecase

import com.simple.cats.domain.repository.BreedRepository
import javax.inject.Inject

class BreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    suspend fun getBreed(q: String) = breedRepository.getBreed(q)
}