package com.sample.neugelb.data.repository

import com.sample.base.SafeApiRequest
import com.sample.neugelb.data.model.toDomain
import com.sample.neugelb.data.remote.MoviesApi
import com.sample.neugelb.domain.model.MoviesResponse
import com.sample.neugelb.domain.repository.MoviesRepository
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : MoviesRepository, SafeApiRequest() {

    override suspend fun getNowPlaying(
        apiKey: String,
        language: String,
        pageIndex: Int
    ): MoviesResponse =
        apiRequest { moviesApi.getNowPlaying(apiKey, language, pageIndex) }.toDomain()

    override suspend fun searchMovie(
        apiKey: String,
        searchQuery: String,
        language: String,
        pageIndex: Int
    ): MoviesResponse =
        apiRequest { moviesApi.searchMovie(apiKey, searchQuery, language, pageIndex) }.toDomain()
}