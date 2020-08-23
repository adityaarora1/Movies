package com.sample.neugelb.data.remote

import com.sample.neugelb.data.model.MoviesResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "language") language: String,
        @Query(value = "page") pageIndex: Int
    ): Response<MoviesResponseData>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "query") searchQuery: String,
        @Query(value = "language") language: String,
        @Query(value = "page") pageIndex: Int
    ): Response<MoviesResponseData>
}