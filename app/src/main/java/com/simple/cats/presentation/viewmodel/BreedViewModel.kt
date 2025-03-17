package com.simple.cats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.cats.domain.model.BreedSearchResponse
import com.simple.cats.domain.usecase.BreedUseCase
import com.simple.cats.presentation.util.CatResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val breedUseCase: BreedUseCase
) : ViewModel() {

    private val _breedSearchResultFlow = MutableStateFlow<CatResult<BreedSearchResponse>>(CatResult.None)
    val breedSearchResultFlow = _breedSearchResultFlow.asStateFlow()

    init {
        getBreedSearch()
    }

    fun getBreedSearch(query: String = "") {
        _breedSearchResultFlow.update { CatResult.Loading }
        viewModelScope.launch {
            val result = breedUseCase.getBreed(query)
            _breedSearchResultFlow.update { result }
        }
    }
}