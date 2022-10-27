package com.test.newsapp.domain.use_case

import com.test.newsapp.data.models.News
import com.test.newsapp.data.utils.Result
import com.test.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDefaultNews(
    private val repository: NewsRepository
) {

    operator fun invoke(page: Int): Flow<Result<List<News>>> = repository.getDefaultNews(page).map { result ->
        when (result) {
            is Result.Success -> {
                Result.Success(result.data?.toNewsList() ?: emptyList())
            }
            is Result.Error -> {
                Result.Error(message = result.message)
            }
            is Result.Loading -> Result.Loading()
        }
    }
}