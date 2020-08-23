package com.sample.neugelb.domain.model

data class MoviesResponse(
    val page: Int,
    val movies: List<Movie>,
    val dates: Dates?,
    val totalPages: Int,
    val totalResults: Int
)