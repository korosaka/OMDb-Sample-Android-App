package com.lyft.android.omdbsampleproject.view_model

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class SearchingMoviesViewModelTest {

    private lateinit var viewModel: SearchingMoviesViewModel

    @Before
    fun setUp() {
        viewModel = SearchingMoviesViewModel()
    }

    @Test
    fun fetchMoviesTest() {
        runBlocking {
            viewModel.fetchMoviesExceptPoster()
        }
        assertEquals(10, viewModel.movies.size)
        assertTrue(viewModel.movies.size == 10)
    }

}