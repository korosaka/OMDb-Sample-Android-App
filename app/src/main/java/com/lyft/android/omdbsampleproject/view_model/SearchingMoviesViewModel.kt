package com.lyft.android.omdbsampleproject.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.repository.MockMovieRepository
import com.lyft.android.omdbsampleproject.repository.MovieImageRepository
import com.lyft.android.omdbsampleproject.repository.MovieRepositoryImpl
import com.lyft.android.omdbsampleproject.repository.MovieRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchingMoviesViewModel(private val movieRepo: MovieRepositoryInterface = MovieRepositoryImpl()) :
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

    fun onClickSearchButton() {
        if (liveSearchingTitle.value.isNullOrBlank()) return
        currentTitle = liveSearchingTitle.value!!
        currentYear = liveSearchingYear.value
        currentPage = 1

        fetchMovies()
    }

    fun onClickBackPage() {
        if (currentPage < 2) return
        currentPage--

        fetchMovies()
    }

    fun onClickNextPage() {
        if (currentPage == lastPage) return
        currentPage++

        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMoviesExceptPoster()
            updateLiveMovies()
            updatePageDisplay()

            val imageRepo = MovieImageRepository()
            for (movie in movies) {
                movie.poster = imageRepo.fetchImage(movie.posterUrl)
                updateLiveMovies()
            }
        }
    }

    suspend fun fetchMoviesExceptPoster() {
        val resultInfo = withContext(Dispatchers.IO) {
            movieRepo.fetchMoviesInfo(currentTitle, currentYear, currentPage)
        }
        movies.clear()
        movies.addAll(resultInfo.movies)

        lastPage = resultInfo.total / COUNT_PER_PAGE
        if (resultInfo.total % COUNT_PER_PAGE > 0) lastPage++
    }

    private fun updateLiveMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            liveMovies.value = movies
        }
    }

    private fun updatePageDisplay() {
        viewModelScope.launch(Dispatchers.Main) {
            pageDisplay.value = "Page$currentPage in $lastPage"
        }
    }
}