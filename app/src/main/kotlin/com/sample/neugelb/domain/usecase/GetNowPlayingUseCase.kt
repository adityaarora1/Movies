package com.sample.neugelb.domain.usecase

import com.sample.neugelb.BuildConfig
import com.sample.neugelb.domain.model.MoviesResponse
import com.sample.neugelb.domain.repository.MoviesRepository
import java.util.*
import javax.inject.Inject

class GetNowPlayingUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getNowPlaying(page: Int): MoviesResponse = moviesRepository.getNowPlaying(
        apiKey = BuildConfig.API_KEY,
        language = Locale.getDefault().language,
        pageIndex = page
    )
}
