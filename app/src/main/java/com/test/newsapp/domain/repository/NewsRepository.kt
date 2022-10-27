package com.test.newsapp.domain.repository

import com.test.newsapp.data.dto.NewsListDto
import com.test.newsapp.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getDefaultNews(page: Int): Flow<Result<NewsListDto>>
}