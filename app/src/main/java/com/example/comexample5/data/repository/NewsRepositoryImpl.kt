package com.example.comexample5.data.repository

import com.example.comexample5.data.remote.api.RetrofitClient
import com.example.comexample5.domain.model.NewsArticle
import com.example.comexample5.domain.model.NewsCategory
import com.example.comexample5.domain.model.NewsSource
import com.example.comexample5.domain.repository.NewsRepository
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl : NewsRepository {

    override suspend fun getNews(): List<NewsArticle> {
        try {
            val posts = RetrofitClient.postsApi.getPosts()

            return posts.map { post ->
                NewsArticle(
                    id = post.id,
                    title = post.title,
                    description = post.body,
                    category = NewsCategory.TECHNOLOGY,
                    source = NewsSource(
                        name = "JSONPlaceholder",
                        country = "Test API"
                    ),
                    publishedAt = "Завантажено з API"
                )
            }

        } catch (e: IOException) {
            throw Exception(
                "Немає підключення до Інтернету",
                e
            )

        } catch (e: HttpException) {
            throw Exception(
                "Помилка сервера: ${e.code()}",
                e
            )

        } catch (e: Exception) {
            throw Exception(
                "Не вдалося завантажити новини",
                e
            )
        }
    }
}