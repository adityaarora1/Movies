package com.sample.neugelb.data.repository

import com.sample.base.ApiException
import com.sample.neugelb.BuildConfig
import com.sample.neugelb.DataFixtures
import com.sample.neugelb.data.model.toDomain
import com.sample.neugelb.data.remote.MoviesApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.util.*

@RunWith(JUnit4::class)
class MoviesRepositoryImplTest {

    @MockK
    internal lateinit var moviesApi: MoviesApi
    private lateinit var moviesRepositoryImpl: MoviesRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        moviesRepositoryImpl = MoviesRepositoryImpl(moviesApi)
    }

    @Test
    fun `repository fetches now playing movies and maps to domain model`() = runBlocking {
        coEvery {
            moviesApi.getNowPlaying(
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } returns Response.success(DataFixtures.getMoviesResponseData())

        val result = moviesRepositoryImpl.getNowPlaying(
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertEquals(result, DataFixtures.getMoviesResponseData().toDomain())
    }

    @Test(expected = ApiException::class)
    fun `repository fails to fetch now playing movies`() = runBlocking {
        coEvery {
            moviesApi.getNowPlaying(
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } throws ApiException("Invalid API Key")

        val result = moviesRepositoryImpl.getNowPlaying(
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertNull(result)
    }

    @Test
    fun `repository fetches now playing movies and returns empty`() = runBlocking {
        coEvery {
            moviesApi.getNowPlaying(
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } returns Response.success(DataFixtures.getEmptyMoviesResponseData())

        val result = moviesRepositoryImpl.getNowPlaying(
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertEquals(result, DataFixtures.getEmptyMoviesResponseData().toDomain())
        assertNotNull(result.movies)
        assert(result.movies.count() == 0)
    }

    @Test
    fun `repository searches movie and maps to domain model`() = runBlocking {
        coEvery {
            moviesApi.searchMovie(
                searchQuery = "Scooby",
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } returns Response.success(DataFixtures.getMoviesResponseData())

        val result = moviesRepositoryImpl.searchMovie(
            searchQuery = "Scooby",
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertEquals(result, DataFixtures.getMoviesResponseData().toDomain())
        assertNotNull(result.movies)
        assert(result.movies.count() == 1)
    }

    @Test(expected = ApiException::class)
    fun `repository fails to search movie`() = runBlocking {
        coEvery {
            moviesApi.searchMovie(
                searchQuery = "Scooby",
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } throws ApiException("Invalid API Key")

        val result = moviesRepositoryImpl.searchMovie(
            searchQuery = "Scooby",
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertNull(result)
    }


    @Test
    fun `repository searches movie and returns empty`() = runBlocking {
        coEvery {
            moviesApi.searchMovie(
                searchQuery = "",
                apiKey = BuildConfig.API_KEY,
                language = Locale.getDefault().language,
                pageIndex = 1
            )
        } returns Response.success(DataFixtures.getEmptyMoviesResponseData())

        val result = moviesRepositoryImpl.searchMovie(
            searchQuery = "",
            apiKey = BuildConfig.API_KEY,
            language = Locale.getDefault().language,
            pageIndex = 1
        )
        assertEquals(result, DataFixtures.getEmptyMoviesResponseData().toDomain())
        assertNotNull(result.movies)
        assert(result.movies.count() == 0)
    }
}