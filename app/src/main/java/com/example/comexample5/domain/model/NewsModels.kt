
package com.example.comexample5.domain.model

// Категорії новин
enum class NewsCategory {
    TECHNOLOGY,
    POLITICS,
    BUSINESS,
    SCIENCE,
    SPORTS
}

// Джерело новини
data class NewsSource(
    val name: String,
    val country: String
)

// Модель новини
data class NewsArticle(
    val id: Int,
    val title: String,
    val description: String,
    val category: NewsCategory,
    val source: NewsSource,
    val publishedAt: String,
    val savedOffline: Boolean = false
)

// Результат завантаження даних
sealed class NewsResult<out T> {
    data class Success<T>(val data: T) : NewsResult<T>()
    data class Error(val message: String) : NewsResult<Nothing>()
    object Loading : NewsResult<Nothing>()
}