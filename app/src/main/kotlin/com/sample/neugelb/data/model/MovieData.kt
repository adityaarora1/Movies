package com.sample.neugelb.data.model

import com.sample.neugelb.domain.model.Movie
import com.squareup.moshi.JsonClass
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieData(
    val poster_path: String?,
    val adult: Boolean?,
    val overview: String?,
    val release_date: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_title: String?,
    val original_language: String?,
    val title: String?,
    val backdrop_path: String?,
    val popularity: Double?,
    val vote_count: Int?,
    val video: Boolean?,
    val vote_average: Double?
)

internal fun List<MovieData>.toDomain(): List<Movie> = map {
    Movie(
        poster_path = it.poster_path ?: "",
        adult = it.adult ?: false,
        overview = it.overview ?: "",
        release_date = transformDate(it.release_date ?: ""),
        genre_ids = it.genre_ids ?: emptyList(),
        id = it.id!!,
        original_title = it.original_title ?: "",
        original_language = it.original_language ?: "",
        title = it.title ?: "",
        backdrop_path = it.backdrop_path ?: "",
        popularity = it.popularity ?: 0.00,
        vote_count = it.vote_count ?: 0,
        video = it.video ?: false,
        vote_average = it.vote_average ?: 0.00
    )
}

fun transformDate(dateString: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val sdfFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    try {
        val date: Date = sdf.parse(dateString)
        return sdfFormatter.format(date)
    } catch (e: ParseException) {
        Timber.e(e)
    }
    return dateString
}