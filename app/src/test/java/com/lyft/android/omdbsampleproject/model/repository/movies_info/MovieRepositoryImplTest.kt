package com.lyft.android.omdbsampleproject.model.repository.movies_info

import com.lyft.android.omdbsampleproject.view_model.SearchingMoviesViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

//import org.junit.jupiter.api.Assertions.*

internal class MovieRepositoryImplTest {

    private lateinit var movieRepo: MovieRepositoryImpl

    @Before
    fun setUp() {
        movieRepo = MovieRepositoryImpl()
    }

    @Test
    fun fetchMoviesInfoTest() {
        val resultInfo = movieRepo.fetchMoviesInfo("love", null, 1)
        assertTrue("resultInfo is not null", resultInfo != null)
        assertTrue("resultInfo.movie is not null or empty", !resultInfo?.movies.isNullOrEmpty())
        assertEquals(SearchingMoviesViewModel.COUNT_PER_PAGE, resultInfo?.movies?.size)
        assertTrue("resultInfo.total is more than 100", resultInfo?.total!! == 20728)
    }
}
