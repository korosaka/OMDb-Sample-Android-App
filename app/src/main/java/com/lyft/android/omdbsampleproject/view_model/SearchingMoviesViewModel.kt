package com.lyft.android.omdbsampleproject.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.repository.MockMovieRepository
import com.lyft.android.omdbsampleproject.repository.MovieRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchingMoviesViewModel(private val movieRepo: MovieRepositoryInterface = MockMovieRepository()) :
    ViewModel() {

    val liveMovies = MutableLiveData<List<MovieData>>()
    val liveSearchingTitle = MutableLiveData<String>()
    val liveSearchingYear = MutableLiveData<String>()
    val pageDisplay = MutableLiveData<String>()

    val movies: MutableList<MovieData> = mutableListOf()
    private var currentTitle = ""
    private var currentYear: String? = null
    private var currentPage = 1
    private var lastPage = 1

    companion object {
        const val COUNT_PER_PAGE = 10
    }

    init {
        liveMovies.value = movies
        liveSearchingTitle.value = ""
        liveSearchingYear.value = ""
        pageDisplay.value = ""
    }

    fun fetchMovies(completeListener: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMoviesExceptPoster()
        }
        completeListener()
    }

    suspend fun fetchMoviesExceptPoster() {
        val resultInfo = withContext(Dispatchers.IO) {
            movieRepo.fetchMoviesInfo(liveSearchingTitle.value ?: "", currentYear)
        }
        movies.clear()
        movies.addAll(resultInfo.movies)

        lastPage = resultInfo.total / COUNT_PER_PAGE
        if (resultInfo.total % COUNT_PER_PAGE > 0) lastPage++
        viewModelScope.launch(Dispatchers.Main) {
            updatePageDisplay()
        }
    }

    private fun updatePageDisplay() {
        pageDisplay.value = "Page$currentPage in $lastPage"
    }
}