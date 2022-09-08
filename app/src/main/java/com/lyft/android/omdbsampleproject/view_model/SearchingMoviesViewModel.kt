package com.lyft.android.omdbsampleproject.view_model

import android.app.Application
import androidx.lifecycle.*
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.model.MySharedPreference
import com.lyft.android.omdbsampleproject.model.repository.movie_image.MovieImageRepository
import com.lyft.android.omdbsampleproject.model.repository.movies_info.MovieRepositoryImpl
import com.lyft.android.omdbsampleproject.model.repository.movies_info.MovieRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchingMoviesViewModel(
    application: Application,
    private val movieRepo: MovieRepositoryInterface = MovieRepositoryImpl()
) :
    AndroidViewModel(application) {

    /**
     * When ViewModel has arguments in the constructor,
     * "Factory" class is needed!
     */
    class SearchingMoviesFactory(
        private val application: Application,
        private val movieRepo: MovieRepositoryInterface = MovieRepositoryImpl()
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchingMoviesViewModel(application, movieRepo) as T
        }
    }


    val liveMovies = MutableLiveData<List<MovieData>>()
    val liveSearchingTitle = MutableLiveData<String>()
    val liveSearchingYear = MutableLiveData<String>()
    val livePageDisplay = MutableLiveData<String>()
    val livePlot = MutableLiveData<String>()

    /**
     * To keep same instance for List, "movies" is used
     * because LiveData.value can't use clear(), for example.
     * The reason to keep the List instance is to avoid recreate Adapter for RecyclerView.
     * If the different instance is set to the value, notify methods never work.
     */
    val movies: MutableList<MovieData> = mutableListOf()

    private var currentTitle = EMPTY
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
        const val EMPTY = ""
    }

    init {
        liveMovies.value = movies
        liveSearchingTitle.value = EMPTY
        liveSearchingYear.value = EMPTY
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

        fetchMoviesInIO()
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

        fetchMoviesInIO()
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

        fetchMoviesInIO()
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

    private fun fetchMoviesInIO() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMovies()
        }
    }

    suspend fun fetchMovies() {
        resetPlot()
        fetchMoviesExceptPoster()

        /**
         * fetchMoviesExceptPoster() is a suspend function.
         * This is why the below functions are going to run only after completing fetching movies' info(except Poster)
         */
        updateLiveMovies()
        updatePageDisplay()
        resetScroll()

        applyFavToMovies()
        fetchMoviesPoster()
    }

    private suspend fun fetchMoviesExceptPoster() {
        /**
         * "withContext(Dispatchers)" is a suspend function.
         * This is why "fetchMoviesExceptPoster()" needs to be suspend too
         * as long as not using "viewModelScope.launch".
         */
        val resultInfo = withContext(Dispatchers.IO) {
            movieRepo.fetchMoviesInfo(currentTitle, currentYear, currentPage)
        }
        /**
         * Because withContext(Dispatchers) is a suspend function,
         * the below functions start only after fetching resultInfo.
         */
        movies.clear()

        if (resultInfo == null) searchResultStatus = ERROR_RESULT
        else if (resultInfo.movies.isEmpty() || resultInfo.total == 0)
            searchResultStatus = EMPTY_RESULT
        else {
            searchResultStatus = SUCCESS_RESULT
            movies.addAll(resultInfo.movies)
            calcLastPage(resultInfo.total)
        }
    }

    private fun calcLastPage(totalCount: Int) {
        lastPage = totalCount / COUNT_PER_PAGE
        if (totalCount % COUNT_PER_PAGE > 0) lastPage++
    }

    private suspend fun fetchMoviesPoster() {
        withContext(Dispatchers.IO) {
            val imageRepo = MovieImageRepository()
            /***
             * Using try-catch to avoid ConcurrentModificationException when the Back/Next button is tapped fast
             */
            try {
                for (movie in movies) {
                    movie.poster = imageRepo.fetchImage(movie.posterUrl)
                    updateLiveMovies()
                }
            } catch (e: Exception) {
                return@withContext
            }
        }
    }

    /**
     * To be observed in View and update the movie list view(RecyclerView),
     * this substitution to Livedata is needed although the Livedata has already had this data(reference to "movies").
     */
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

    fun onClickFav(id: String) {
        MySharedPreference().saveFavorites(id, getApplication<Application>().applicationContext)
        applyFavToMovies()
    }

    private fun applyFavToMovies() {
        val favIds = MySharedPreference().getFavorites(getApplication<Application>().applicationContext)
        for (movie in movies) {
            if (favIds.contains(movie.id)) movie.isFav = true
        }
        updateLiveMovies()
    }

    /**
     * In MVVM, ViewModel can't refer View.
     * This is why through Interface, update View.
     */
    interface SearchingListener {
        fun showToast(message: String)
        fun resetScroll()
    }
}