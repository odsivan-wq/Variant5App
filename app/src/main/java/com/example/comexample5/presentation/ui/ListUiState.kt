
package com.example.comexample5.presentation.ui

import com.example.comexample5.domain.model.NewsArticle

data class ListUiState(
    val news: List<NewsArticle> = emptyList(),
    val searchText: String = "",
    val isLoading: Boolean = false,
    val selectedArticleId: Int? = null,
    val errorMessage: String? = null
) {
    val filteredNews: List<NewsArticle>
        get() = news.filter {
            it.title.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true)
        }
}