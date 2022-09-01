package com.lyft.android.omdbsampleproject.model.repository.movies_info

import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo

interface MovieRepositoryInterface {
    fun fetchMoviesInfo(title: String, year: String?, page: Int = 1): ResultMoviesInfo?
    fun fetchMoviePlot(id: String): String?
}