package com.sample.neugelb.data.model

import com.sample.neugelb.DataFixtures
import com.sample.neugelb.DataFixtures.getMoviesResponseData
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesResponseDataTest {

    @Test
    fun `movies response data to domain`() {
        val moviesDataResponse = getMoviesResponseData()
        val movies = DataFixtures.getMoviesResponse()

        assertEquals(movies.dates, moviesDataResponse.dates?.toDomain())
        assertEquals(movies.movies, moviesDataResponse.results.toDomain())
        assertEquals(movies.page, moviesDataResponse.page)
        assertEquals(movies.totalPages, moviesDataResponse.total_pages)
        assertEquals(movies.totalResults, moviesDataResponse.total_results)
    }
}