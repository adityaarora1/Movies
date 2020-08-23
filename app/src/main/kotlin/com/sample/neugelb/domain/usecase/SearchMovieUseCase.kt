package com.sample.neugelb.domain.usecase

import com.sample.neugelb.BuildConfig
import com.sample.neugelb.domain.model.MoviesResponse
import com.sample.neugelb.domain.repository.MoviesRepository
import java.util.*
import javax.inject.Inject

internal class SearchMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun searchMovie(
        searchQuery: String,
        page: Int
    ): MoviesResponse =
        moviesRepository.searchMovie(
            apiKey = BuildConfig.API_KEY,
            searchQuery = searchQuery,
            language = Locale.getDefault().language,
            pageIndex = page
        )
}
