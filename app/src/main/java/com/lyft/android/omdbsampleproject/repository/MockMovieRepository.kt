package com.lyft.android.omdbsampleproject.repository

import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo

class MockMovieRepository : MovieRepositoryInterface {

    override fun fetchMoviesInfo(title: String, year: String?, page: Int): ResultMoviesInfo {
        val mutableList = mutableListOf<MovieData>()
        for (movie in mockMovieData[page]) mutableList.add(movie)
        return ResultMoviesInfo(mutableList, countTotal())
    }

    private val mockMovieData = arrayOf(
        arrayOf(
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
        ),
        arrayOf(
            MovieData("test_id11", "Test Title1", "1991", "test_url", null),
            MovieData("test_id12", "Test Title2", "1992", "test_url", null),
            MovieData("test_id13", "Test Title3", "1993", "test_url", null),
            MovieData("test_id14", "Test Title4", "1994", "test_url", null),
            MovieData("test_id15", "Test Title5", "1995", "test_url", null),
            MovieData("test_id16", "Test Title6", "1996", "test_url", null),
            MovieData("test_id17", "Test Title7", "1997", "test_url", null),
            MovieData("test_id18", "Test Title8", "1998", "test_url", null),
            MovieData("test_id19", "Test Title9", "1999", "test_url", null),
            MovieData("test_id20", "Test Title10", "2000", "test_url", null)
        ),
        arrayOf(
            MovieData("test_id21", "Test Title1", "1991", "test_url", null),
            MovieData("test_id22", "Test Title2", "1992", "test_url", null),
            MovieData("test_id23", "Test Title3", "1993", "test_url", null),
        )
    )

    private fun countTotal(): Int {
        var counter = 0
        for (movies in mockMovieData) {
            for (movie in movies) counter++
        }
        return counter
    }
}