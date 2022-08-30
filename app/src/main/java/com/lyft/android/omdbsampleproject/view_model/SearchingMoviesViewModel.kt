package com.lyft.android.omdbsampleproject.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.repository.MockMovieRepository
import com.lyft.android.omdbsampleproject.repository.MovieRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchingMoviesViewModel(val movieRepo: MovieRepositoryInterface = MockMovieRepository()) :
    ViewModel() {

    val movies: MutableList<MovieData> = mutableListOf()
    private var currentTitle = ""
    private var currentYear: String? = null
    private var currentPage = 1


    suspend fun fetchMovies() {
        val fetchedMovies = withContext(Dispatchers.IO) {
            movieRepo.fetchMoviesData(currentTitle, currentYear)
        }
        movies.clear()
        movies.addAll(fetchedMovies)
    }
}