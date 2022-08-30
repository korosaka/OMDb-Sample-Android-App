package com.lyft.android.omdbsampleproject.repository

import com.lyft.android.omdbsampleproject.model.MovieData

class MockMovieRepository {

    fun fetchMoviesData(title: String, year: String, page: Int = 1): List<MovieData> {
        val mutableList = mutableListOf<MovieData>()
        for (movie in mockMovieData) mutableList.add(movie)
        return mutableList.toList()
    }

    private val mockMovieData = arrayOf(
        MovieData("test_id1", "Test Title1", "1991", "test_url", null),
        MovieData("test_id2", "Test Title2", "1992", "test_url", null),
        MovieData("test_id3", "Test Title3", "1993", "test_url", null),
        MovieData("test_id4", "Test Title4", "1994", "test_url", null),
        MovieData("test_id5", "Test Title5", "1995", "test_url", null),
        MovieData("test_id6", "Test Title6", "1996", "test_url", null),
        MovieData("test_id7", "Test Title7", "1997", "test_url", null),
        MovieData("test_id8", "Test Title8", "1998", "test_url", null),
        MovieData("test_id9", "Test Title9", "1999", "test_url", null),
        MovieData("test_id10", "Test Title10", "2000", "test_url", null)
    )
}