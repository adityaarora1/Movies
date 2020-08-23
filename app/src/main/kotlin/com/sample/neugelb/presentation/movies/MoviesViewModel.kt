package com.sample.neugelb.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.neugelb.domain.model.Movie
import com.sample.neugelb.domain.usecase.GetNowPlayingUseCase
import com.sample.neugelb.domain.usecase.MoviesDataSource
import com.sample.neugelb.domain.usecase.SearchMovieDataSource
import com.sample.neugelb.domain.usecase.SearchMovieUseCase
import kotlinx.coroutines.flow.Flow

internal class MoviesViewModel @ViewModelInject constructor(
    private val getNowPlayingUseCase: GetNowPlayingUseCase,
    private val searchMovieUseCase: SearchMovieUseCase

) : ViewModel() {

    private val pageSize = 20

    private var currentQueryValue: String? = null
    private lateinit var searchResult: Flow<PagingData<Movie>>
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    val selected = MutableLiveData<Movie>()

    fun select(item: Movie) {
        selected.value = item
    }

    fun searchMovie(query: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) return lastResult
        currentQueryValue = query
        searchResult = Pager(PagingConfig(pageSize = pageSize)) {
            SearchMovieDataSource(useCase = searchMovieUseCase).apply {
                this.setQuery(query)
            }
        }.flow.cachedIn(viewModelScope)
        currentSearchResult = searchResult
        return searchResult
    }

    fun nowPlaying(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = pageSize)) {
            MoviesDataSource(useCase = getNowPlayingUseCase)
        }.flow.cachedIn(viewModelScope)
    }
}