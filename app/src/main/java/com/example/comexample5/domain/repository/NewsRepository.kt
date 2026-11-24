
package com.example.comexample5.domain.repository

import com.example.comexample5.domain.model.NewsArticle

interface NewsRepository {

    suspend fun getNews(): List<NewsArticle>
}