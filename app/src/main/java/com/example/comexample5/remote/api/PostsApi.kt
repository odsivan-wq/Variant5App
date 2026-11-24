package com.example.comexample5.data.remote.api

import com.example.comexample5.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostsApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}