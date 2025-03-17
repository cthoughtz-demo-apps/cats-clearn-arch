package com.simple.cats.data.di

import com.simple.cats.domain.repository.BreedFactsRepository
import com.simple.cats.domain.repository.BreedFactsRepositoryImpl
import com.simple.cats.domain.repository.BreedRepository
import com.simple.cats.domain.repository.BreedRepositoryImpl
import com.simple.cats.domain.repository.ImageSearchRepository
import com.simple.cats.domain.repository.ImageSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Singleton
    @Binds
    abstract fun provideImageSearchRepository(imageSearchRepositoryImpl: ImageSearchRepositoryImpl): ImageSearchRepository

    @Singleton
    @Binds
    abstract fun provideBreedRepository(breedRepositoryImpl: BreedRepositoryImpl): BreedRepository

    @Singleton
    @Binds
    abstract fun provideBreedFactsRepository(breedFactsRepositoryImpl: BreedFactsRepositoryImpl): BreedFactsRepository
}