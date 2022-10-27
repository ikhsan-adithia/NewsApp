package com.test.newsapp.presentation.news_list

import com.test.newsapp.data.models.News

data class NewsListState(
    val isLoading: Boolean = false,
    val news: List<News> = emptyList(),
    val currentPage: Int = 1,
    val allowLoadMore: Boolean = true
)
