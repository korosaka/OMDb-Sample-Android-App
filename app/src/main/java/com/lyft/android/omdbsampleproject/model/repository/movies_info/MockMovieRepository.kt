package com.lyft.android.omdbsampleproject.model.repository.movies_info

import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo

class MockMovieRepository : MovieRepositoryInterface {

    override fun fetchMoviesInfo(title: String, year: String?, page: Int): ResultMoviesInfo {
        val mutableList = mutableListOf<MovieData>()
        val targetPageIndex = page - 1
        for (movie in mockMovieData[targetPageIndex]) mutableList.add(movie)
        Thread.sleep(2000)
        return ResultMoviesInfo(mutableList, countTotal())
    }

    override fun fetchMoviePlot(id: String): String? {
        return null
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
            MovieData("test_id11", "Test Title11", "1991", "test_url", null),
            MovieData("test_id12", "Test Title12", "1992", "test_url", null),
            MovieData("test_id13", "Test Title13", "1993", "test_url", null),
            MovieData("test_id14", "Test Title14", "1994", "test_url", null),
            MovieData("test_id15", "Test Title15", "1995", "test_url", null),
            MovieData("test_id16", "Test Title16", "1996", "test_url", null),
            MovieData("test_id17", "Test Title17", "1997", "test_url", null),
            MovieData("test_id18", "Test Title18", "1998", "test_url", null),
            MovieData("test_id19", "Test Title19", "1999", "test_url", null),
            MovieData("test_id20", "Test Title20", "2000", "test_url", null)
        ),
        arrayOf(
            MovieData("test_id21", "Test Title21", "1991", "test_url", null),
            MovieData("test_id22", "Test Title22", "1992", "test_url", null),
            MovieData("test_id23", "Test Title23", "1993", "test_url", null),
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