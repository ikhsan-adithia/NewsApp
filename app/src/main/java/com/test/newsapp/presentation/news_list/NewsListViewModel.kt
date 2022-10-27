package com.test.newsapp.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.newsapp.data.utils.Result
import com.test.newsapp.domain.use_case.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val useCases: NewsUseCases
): ViewModel() {

    private val _state = MutableStateFlow(NewsListState())
    val state get(): StateFlow<NewsListState> = _state

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage get() = _errorMessage.asSharedFlow()

    init {
        getDefaultNewsList(1)
    }

    fun loadMoreNews() {
        if (state.value.allowLoadMore) {
            getDefaultNewsList(state.value.currentPage + 1)
        }
    }

    private var job: Job? = null
    private fun getDefaultNewsList(page: Int) {
        job?.cancel()
        job = useCases.getDefaultNews(page)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                news = if (page > 1) {
                                    it.news + (result.data ?: emptyList())
                                } else {
                                    result.data ?: emptyList()
                                },
                                currentPage = page,
                                allowLoadMore = (result.data ?: emptyList()).isNotEmpty()
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        result.message?.let { _errorMessage.emit(it) }
                    }
                    is Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
            .launchIn(viewModelScope.plus(Dispatchers.IO))
    }
}