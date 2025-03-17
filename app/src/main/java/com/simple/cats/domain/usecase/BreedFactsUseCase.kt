package com.simple.cats.domain.usecase

import com.simple.cats.domain.repository.BreedFactsRepository
import javax.inject.Inject

class BreedFactsUseCase @Inject constructor(
    private val breedFactsRepository: BreedFactsRepository
) {
    suspend fun getFacts(breed: Int) = breedFactsRepository.getFacts(breed)
}