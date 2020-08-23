package com.sample.neugelb.data.remote

object ImageApi {
  private const val BASE_THUMBNAIL_PATH = "https://image.tmdb.org/t/p/w185"
  private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/original"

  fun getThumbnailPath(posterPath: String): String {
    return BASE_THUMBNAIL_PATH + posterPath
  }

  fun getPosterPath(posterPath: String): String {
    return BASE_POSTER_PATH + posterPath
  }
}
