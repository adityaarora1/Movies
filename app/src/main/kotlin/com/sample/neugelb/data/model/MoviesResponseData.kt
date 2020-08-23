package com.sample.neugelb.data.model

import com.sample.neugelb.domain.model.MoviesResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponseData(
    val page: Int,
    val results: List<MovieData>,
    val dates: DatesData?,
    val total_pages: Int,
    val total_results: Int
)

internal fun MoviesResponseData.toDomain(): MoviesResponse = MoviesResponse(
    page = page,
    movies = results.filter { it.id != null }.toDomain(),
    dates = dates?.toDomain(),
    totalPages = total_pages,
    totalResults = total_results
)
