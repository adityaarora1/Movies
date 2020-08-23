package com.sample.neugelb.domain.usecase

import androidx.paging.PagingSource
import com.sample.neugelb.domain.model.Movie

internal class MoviesDataSource(
    private val useCase: GetNowPlayingUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = useCase.getNowPlaying(currentLoadingPageKey)
            val responseData = mutableListOf<Movie>()
            val data = response.movies
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}