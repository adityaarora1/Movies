package com.sample.neugelb.domain.usecase

import com.sample.neugelb.BuildConfig
import com.sample.neugelb.DataFixtures
import com.sample.neugelb.data.model.toDomain
import com.sample.neugelb.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class SearchMovieUseCaseTest {

    @MockK
    internal lateinit var mockRepository: MoviesRepository
    private lateinit var searchMovieUseCase: SearchMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchMovieUseCase = SearchMovieUseCase(mockRepository)
    }

    @Test
    fun `return now playing movies`() = runBlocking {
        val movieResponse = DataFixtures.getMoviesResponseData().toDomain()

        coEvery {
            mockRepository.searchMovie(
                searchQuery = "scooby",
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } returns movieResponse

        val result = searchMovieUseCase.searchMovie("scooby", 1)

        assertEquals(result, movieResponse)
    }
}