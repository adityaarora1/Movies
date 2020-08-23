package com.sample.neugelb.data.model

import com.sample.neugelb.DataFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDataTest {

    @Test
    fun `movie data to domain transformation`() {
        val actualMovieData = DataFixtures.results
        val expectedMovie = DataFixtures.movies

        assertEquals(expectedMovie, actualMovieData.toDomain())
    }
}