
package com.example.comexample5.domain.usecase

import com.example.comexample5.domain.model.NewsArticle
import com.example.comexample5.domain.repository.NewsRepository

class GetNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<NewsArticle> {
        return repository.getNews()
    }
}