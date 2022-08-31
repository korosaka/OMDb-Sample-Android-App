package com.lyft.android.omdbsampleproject.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lyft.android.omdbsampleproject.repository.MockMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SearchingMoviesViewModelTest {

    private lateinit var viewModel: SearchingMoviesViewModel

    //for LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = SearchingMoviesViewModel(MockMovieRepository())
    }

    @Test
    fun fetchMoviesTest() {
        runBlocking {
            viewModel.fetchMoviesExceptPoster()
        }
        assertEquals(10, viewModel.movies.size)
    }

}