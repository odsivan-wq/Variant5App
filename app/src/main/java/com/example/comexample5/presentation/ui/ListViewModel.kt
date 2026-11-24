package com.example.comexample5.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comexample5.data.repository.NewsRepositoryImpl
import com.example.comexample5.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val getNewsUseCase: GetNewsUseCase =
        GetNewsUseCase(NewsRepositoryImpl())
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ListUiState(isLoading = true))

    val uiState = _uiState.asStateFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val news = getNewsUseCase()

                _uiState.update {
                    it.copy(
                        news = news,
                        isLoading = false,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                            ?: "Помилка завантаження новин"
                    )
                }
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    fun onArticleSelected(id: Int) {
        _uiState.update {
            it.copy(selectedArticleId = id)
        }
    }
}