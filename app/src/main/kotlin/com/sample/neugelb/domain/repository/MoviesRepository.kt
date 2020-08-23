package com.sample.neugelb.domain.repository

import com.sample.neugelb.domain.model.MoviesResponse

interface MoviesRepository {

    suspend fun getNowPlaying(
        apiKey: String,
        language: String,
        pageIndex: Int
    ): MoviesResponse

    suspend fun searchMovie(
        apiKey: String,
        searchQuery: String,
        language: String,
        pageIndex: Int
    ): MoviesResponse
}