package com.sample.neugelb

import com.sample.neugelb.data.model.DatesData
import com.sample.neugelb.data.model.MovieData
import com.sample.neugelb.data.model.MoviesResponseData
import com.sample.neugelb.domain.model.Dates
import com.sample.neugelb.domain.model.Movie
import com.sample.neugelb.domain.model.MoviesResponse

object DataFixtures {

    internal fun getEmptyMoviesResponseData(): MoviesResponseData = MoviesResponseData(
        page = 1,
        results = emptyList(),
        dates = null,
        total_pages = 1,
        total_results = 0
    )

    internal fun getMoviesResponseData(): MoviesResponseData = MoviesResponseData(
        page = 1,
        results = results,
        dates = datesData,
        total_pages = 20,
        total_results = 200
    )

    internal fun getMoviesResponse(): MoviesResponse = MoviesResponse(
        page = 1,
        movies = movies,
        dates = dates,
        totalPages = 20,
        totalResults = 200
    )

    val datesData = DatesData(
        maximum = "2020-08-27",
        minimum = "2020-07-10"
    )

    val dates = Dates(
        maximum = "2020-08-27",
        minimum = "2020-07-10"
    )

    val results = listOf(
        MovieData(
            popularity = 104.397,
            id = 577922,
            video = false,
            vote_count = 4,
            vote_average = 5.0,
            title = "Tenet",
            release_date = "2020-08-22",
            original_language = "en",
            original_title = "Tenet",
            genre_ids = listOf(28, 53),
            backdrop_path = "/6TB7E8xvlCqAWqPdS2fPkdvCVM5.jpg",
            adult = false,
            overview = "Ein Agent wird rekrutiert, um einen besonderen Auftrag auszuführen: Er soll den 3. Weltkrieg verhindern. Diesmal ist jedoch keine nukleare Bedrohung der Grund, sondern es muss eine Person gestoppt werden, welche die Fähigkeit besitzt, die Zeit zu manipulieren.",
            poster_path = "/k68nPLbIST6NP96JmTxmZijEvCA.jpg"
        )
    )

    val movies = listOf(
        Movie(
            popularity = 104.397,
            id = 577922,
            video = false,
            vote_count = 4,
            vote_average = 5.0,
            title = "Tenet",
            release_date = "22 Aug 2020",
            original_language = "en",
            original_title = "Tenet",
            genre_ids = listOf(28, 53),
            backdrop_path = "/6TB7E8xvlCqAWqPdS2fPkdvCVM5.jpg",
            adult = false,
            overview = "Ein Agent wird rekrutiert, um einen besonderen Auftrag auszuführen: Er soll den 3. Weltkrieg verhindern. Diesmal ist jedoch keine nukleare Bedrohung der Grund, sondern es muss eine Person gestoppt werden, welche die Fähigkeit besitzt, die Zeit zu manipulieren.",
            poster_path = "/k68nPLbIST6NP96JmTxmZijEvCA.jpg"
        )
    )
}