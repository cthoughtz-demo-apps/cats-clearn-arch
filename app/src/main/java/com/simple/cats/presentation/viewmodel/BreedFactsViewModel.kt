package com.simple.cats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.cats.domain.model.BreedFactsResponse
import com.simple.cats.domain.usecase.BreedFactsUseCase
import com.simple.cats.presentation.util.CatResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreedFactsViewModel @Inject constructor(
    private val breedFactsUseCase: BreedFactsUseCase
): ViewModel() {

    private val _breedFactsResultFlow = MutableStateFlow<CatResult<BreedFactsResponse>>(CatResult.None)
    val breedFactsResultFlow = _breedFactsResultFlow.asStateFlow()

    init {
        getBreed()
    }

    fun getBreed(breedId: Int = 1) {
        _breedFactsResultFlow.update { CatResult.Loading }
        viewModelScope.launch {
            val result = breedFactsUseCase.getFacts(breedId)
            _breedFactsResultFlow.update { result }
        }
    }
}