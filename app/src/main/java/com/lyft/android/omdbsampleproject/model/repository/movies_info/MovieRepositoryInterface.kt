package com.lyft.android.omdbsampleproject.model.repository.movies_info

import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo

/**
 * Interface for MovieRepository
 * For Unit Test, using it, create Mock Repository
 */
interface MovieRepositoryInterface {
    fun fetchMoviesInfo(title: String, year: String?, page: Int = 1): ResultMoviesInfo?
    fun fetchMoviePlot(id: String): String?
}