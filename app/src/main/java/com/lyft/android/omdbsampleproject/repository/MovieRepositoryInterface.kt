package com.lyft.android.omdbsampleproject.repository

import com.lyft.android.omdbsampleproject.model.MovieData

interface MovieRepositoryInterface {
    fun fetchMoviesData(title: String, year: String?, page: Int = 1): List<MovieData>
}