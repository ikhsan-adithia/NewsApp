package com.test.newsapp.data.remote

import com.test.newsapp.data.dto.NewsListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/top-headlines?country=us&category=business")
    suspend fun getDefaultNews(
        @Query("page") page: Int
    ): Response<NewsListDto>

    // NOTE: this is unsafe, normally I put it in a separate file and then access it later from BuildConfig
    companion object {
        const val BASE_URL = "https://newsapi.org"
    }
}