package com.sample.neugelb.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
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

    var currentQueryValue: String? = null

    val selected = MutableLiveData<Movie>()

    private var moviesLiveData = MutableLiveData<Flow<PagingData<Movie>>>()
    fun getMoviesLiveData(): LiveData<Flow<PagingData<Movie>>> = moviesLiveData

    fun select(item: Movie) {
        selected.value = item
    }

    init {
        nowPlaying()
    }

    fun nowPlaying() {
        moviesLiveData.value = Pager(PagingConfig(pageSize = pageSize)) {
            MoviesDataSource(useCase = getNowPlayingUseCase)
        }.flow.cachedIn(viewModelScope)
    }

    fun searchMovie(query: String) {
        moviesLiveData.value = Pager(PagingConfig(pageSize = pageSize)) {
            SearchMovieDataSource(useCase = searchMovieUseCase).apply {
                this.setQuery(query)
            }
        }.flow.cachedIn(viewModelScope)
    }
}