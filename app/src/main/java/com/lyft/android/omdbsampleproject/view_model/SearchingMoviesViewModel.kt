package com.lyft.android.omdbsampleproject.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.omdbsampleproject.model.MovieData
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
    val livePageDisplay = MutableLiveData<String>()
    val livePlot = MutableLiveData<String>()

    val movies: MutableList<MovieData> = mutableListOf()
    private var currentTitle = ""
    private var currentYear: String? = null
    private var currentPage = DEFAULT_PAGE
    private var lastPage = DEFAULT_PAGE
    private var searchResultStatus = EMPTY_RESULT

    var listener: SearchingListener? = null

    companion object {
        const val COUNT_PER_PAGE = 10
        const val DEFAULT_PAGE = 1
        const val ERROR_RESULT = -1
        const val EMPTY_RESULT = 0
        const val SUCCESS_RESULT = 1
    }

    init {
        liveMovies.value = movies
        liveSearchingTitle.value = ""
        liveSearchingYear.value = ""
        livePageDisplay.value = "Let's search movies!"
        livePlot.value = "Movie Plot"
    }

    fun onClickSearchButton() {
        if (liveSearchingTitle.value.isNullOrBlank()) {
            listener?.showToast("Please enter Title")
            return
        }
        currentTitle = liveSearchingTitle.value!!
        currentYear = liveSearchingYear.value
        currentPage = DEFAULT_PAGE

        fetchMovies()
    }

    fun onClickBackPage() {
        if (movies.isEmpty()) {
            listener?.showToast("There is no movie")
            return
        }
        if (currentPage <= DEFAULT_PAGE) {
            listener?.showToast("Here is the top page")
            return
        }
        currentPage--

        fetchMovies()
    }

    fun onClickNextPage() {
        if (movies.isEmpty()) {
            listener?.showToast("There is no movie")
            return
        }
        if (currentPage == lastPage) {
            listener?.showToast("Here is the last page")
            return
        }
        currentPage++

        fetchMovies()
    }

    fun onClickPlot(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val plot = movieRepo.fetchMoviePlot(id)
            viewModelScope.launch(Dispatchers.Main) {
                plot?.let {
                    livePlot.value = it
                }
            }
        }
    }

    private fun fetchMovies() {
        resetPlot()
        viewModelScope.launch(Dispatchers.IO) {
            fetchMoviesExceptPoster()
            updateLiveMovies()
            updatePageDisplay()
            resetScroll()
            val imageRepo = MovieImageRepository()
            //To avoid ConcurrentModificationException when the Back/Next button is tapped fast
            try {
                for (movie in movies) {
                    movie.poster = imageRepo.fetchImage(movie.posterUrl)
                    updateLiveMovies()
                }
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    suspend fun fetchMoviesExceptPoster() {
        val resultInfo = withContext(Dispatchers.IO) {
            movieRepo.fetchMoviesInfo(currentTitle, currentYear, currentPage)
        }
        movies.clear()

        if (resultInfo == null) searchResultStatus = ERROR_RESULT
        else if (resultInfo.movies.isEmpty() || resultInfo.total == 0)
            searchResultStatus = EMPTY_RESULT
        else {
            searchResultStatus = SUCCESS_RESULT
            movies.addAll(resultInfo.movies)

            lastPage = resultInfo.total / COUNT_PER_PAGE
            if (resultInfo.total % COUNT_PER_PAGE > 0) lastPage++
        }
    }

    private fun updateLiveMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            liveMovies.value = movies
        }
    }

    private fun updatePageDisplay() {
        viewModelScope.launch(Dispatchers.Main) {
            livePageDisplay.value = when (searchResultStatus) {
                SUCCESS_RESULT -> "Page$currentPage in $lastPage"
                EMPTY_RESULT -> "Movie not found!"
                else -> "An error has occurred"
            }
        }
    }

    private fun resetPlot() {
        viewModelScope.launch(Dispatchers.Main) {
            livePlot.value = "Movie Plot"
        }
    }

    private fun resetScroll() {
        viewModelScope.launch(Dispatchers.Main) {
            listener?.resetScroll()
        }
    }

    interface SearchingListener {
        fun showToast(message: String)
        fun resetScroll()
    }
}