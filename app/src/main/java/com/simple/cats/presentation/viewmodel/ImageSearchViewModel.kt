package com.simple.cats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.cats.domain.model.ImageSearchResponse
import com.simple.cats.domain.usecase.ImageSearchUseCase
import com.simple.cats.presentation.util.CatResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val imageSearchUseCase: ImageSearchUseCase
): ViewModel() {

    private val _imageSearchResultFlow = MutableStateFlow<CatResult<ImageSearchResponse>>(CatResult.None)
    val imageSearchResultFlow = _imageSearchResultFlow.asStateFlow()

    init {
        getImageSearch()
    }

    fun getImageSearch() {
        _imageSearchResultFlow.update { CatResult.Loading }
        viewModelScope.launch {
            val result = imageSearchUseCase.imageSearch()
            _imageSearchResultFlow.update { result }
        }
    }
}