package com.test.newsapp.data.repository

import com.test.newsapp.data.dto.NewsListDto
import com.test.newsapp.data.remote.NewsService
import com.test.newsapp.data.utils.Result
import com.test.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val service: NewsService
): NewsRepository {

    override fun getDefaultNews(page: Int): Flow<Result<NewsListDto>> = flow {
        emit(Result.Loading())

        val result = service.getDefaultNews(page)

        if (result.isSuccessful) {
            result.body()?.let { emit(Result.Success(it)) }
        } else {
            emit(Result.Error(message = "${result.code()} ${result.message()}"))
        }
    }
}